package pl.media30.zamowieniapubliczne;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.squareup.otto.Subscribe;

import pl.media30.zamowieniapubliczne.Adapters.MyAdapter;
import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    UltimateRecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;
    int strona = 2;
    boolean wczytane = false;
    Bundle bundle = new Bundle();

    public MainActivityFragment() {
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ActivityResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActResult","Zadzialalo");

        if (requestCode == 2) { //jak rozny od *
            try {
                String parametr = data.getStringExtra("wartoscMiasto");
                if (parametr.equals("*"))
                    parametr = null;
                bundle.putString("wartoscMiasto", parametr);
                RepositoryClass.getInstance().setWyszukiwanieMiasta(parametr);

                parametr = data.getStringExtra("wartoscWoj");
                if (parametr.equals("*"))
                    parametr = null;
                bundle.putString("wartoscWoj", parametr);
                RepositoryClass.getInstance().setWyszukiwanieWojew(parametr);

                parametr = data.getStringExtra("wartoscKod");
                if (parametr.equals("*"))
                    parametr = null;
                bundle.putString("wartoscKod", parametr);
                RepositoryClass.getInstance().setWyszukiwanieKodowPoczt(parametr);

                parametr = data.getStringExtra("wartoscNazwa");
                if (parametr.equals("*"))
                    parametr = null;
                bundle.putString("wartoscNazwa", parametr);
                RepositoryClass.getInstance().setWyszukiwanieZamawNazwa(parametr);

                RepositoryClass.getInstance().deleteDataObjectList();
                Intent intent = getActivity().getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                getActivity().overridePendingTransition(0, 0);
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
                startActivity(intent);
                strona = 1;

            } catch (NullPointerException e) {
                RepositoryClass.getInstance().setWyszukiwanieMiasta(null);
                RepositoryClass.getInstance().setWyszukiwanieWojew(null);
                RepositoryClass.getInstance().setWyszukiwanieKodowPoczt(null);
                RepositoryClass.getInstance().setWyszukiwanieZamawNazwa(null);
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            if (savedInstanceState.getInt("getPos") == -1) {
                bundle.putInt("getPos", 0);
            } else {
                bundle.putInt("getPos", savedInstanceState.getInt("getPos"));
            }
        } catch (Exception e) {
            bundle.putInt("getPos",0);
        }
        try {
            if(savedInstanceState.getString("wartoscMiasto").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieMiasta(null);
            if(savedInstanceState.getString("wartoscWoj").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieMiasta(null);
            if(savedInstanceState.getString("wartoscKod").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieKodowPoczt(null);
            if(savedInstanceState.getString("wartoscNazwa").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieZamawNazwa(null);
            wczytane=false;
        } catch (Exception e) {
        }
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.scrollToPosition(bundle.getInt("getPos"));
    }

    @Override
    public void onResume() {
        super.onResume();

        if (bundle.getInt("getPos") == -1) {
            bundle.putInt("getPos", 0);
        }
        mLayoutManager.scrollToPosition(bundle.getInt("getPos"));
    }

    @Override
    public void onStop() {
        super.onStop();
        ActivityResultBus.getInstance().unregister(mActivityResultSubscriber);
        bundle.putInt("getPos", mLayoutManager.findFirstVisibleItemPosition());
        bundle.putBoolean("getWczytaj", wczytane);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        ActivityResultBus.getInstance().register(mActivityResultSubscriber);

        final ProgressDialog dialog =
                ProgressDialog.show(this.getActivity().getWindow().getContext(), "Trwa wczytywanie danych", "Please Wait...");
        dialog.dismiss();
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane/")
                .build();
        final MojePanstwoService service = restAdapter.create(MojePanstwoService.class);

        mRecyclerView = (UltimateRecyclerView) getView().findViewById(R.id.ultimate_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                bundle.putInt("getPos", mLayoutManager.findFirstCompletelyVisibleItemPosition());
                //Toast.makeText(getActivity(),position,Toast.LENGTH_SHORT).show();
                dialog.show();
                if((RepositoryClass.getInstance().getWyszukiwanieMiasta() == null) && (RepositoryClass.getInstance().getWyszukiwanieWojew() == null) && (RepositoryClass.getInstance().getWyszukiwanieKodowPoczt() == null) && (RepositoryClass.getInstance().getWyszukiwanieZamawNazwa() == null)){
                    service.listOrders(strona, new Callback<BaseListClass>() {
                        @Override
                        public void success(BaseListClass blc, Response response) {
                            int rozmiar = mAdapter.getItemCount();
                            RepositoryClass.getInstance().setBaseListClass(blc);
                            mAdapter = new MyAdapter(RepositoryClass.getInstance().getDataObjectList());
                            mRecyclerView.setAdapter(mAdapter);
                            strona++;
                            mLayoutManager.scrollToPosition(bundle.getInt("getPos"));
                            dialog.dismiss();
                            Log.d("Strona", " bez param strona loadmore: " + strona);
                        }
                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    dialog.show();
                    service.listOrdersWithParameter(strona,RepositoryClass.getInstance().getWyszukiwanieMiasta(), RepositoryClass.getInstance().getWyszukiwanieWojew(), RepositoryClass.getInstance().getWyszukiwanieKodowPoczt(), RepositoryClass.getInstance().getWyszukiwanieZamawNazwa(), new Callback<BaseListClass>() {
                        @Override
                        public void success(BaseListClass blc, Response response) {
                            int rozmiar = mAdapter.getItemCount();
                            RepositoryClass.getInstance().setBaseListClass(blc);
                            mAdapter = new MyAdapter(RepositoryClass.getInstance().getDataObjectList());
                            mRecyclerView.setAdapter(mAdapter);
                            strona++;
                            mLayoutManager.scrollToPosition(bundle.getInt("getPos"));
                            dialog.dismiss();
                            Log.d("Strona", "param strona loadmore: " + strona);
                            Log.d("dddd", "ptessxt: " + RepositoryClass.getInstance().getWyszukiwanieMiasta());
                        }
                        @Override
                        public void failure(RetrofitError retrofitError) {
                            dialog.dismiss();
                        }
                    });
                }
            }
        });

        wczytane = bundle.getBoolean("getWczytaj");
        if (wczytane == false) {
            dialog.show();
            RepositoryClass.getInstance();
            if((RepositoryClass.getInstance().getWyszukiwanieMiasta() == null) && (RepositoryClass.getInstance().getWyszukiwanieWojew() == null) && (RepositoryClass.getInstance().getWyszukiwanieKodowPoczt()==null) && (RepositoryClass.getInstance().getWyszukiwanieZamawNazwa() ==null)){
                service.listOrders(1, new Callback<BaseListClass>() {
                    @Override
                    public void success(BaseListClass blc, Response response) {
                        if (RepositoryClass.getInstance().getDataObjectList()!=null)
                            RepositoryClass.getInstance().deleteDataObjectList();
                        RepositoryClass.getInstance().setBaseListClass(blc);
                        mAdapter = new MyAdapter(RepositoryClass.getInstance().getDataObjectList());
                        mRecyclerView.setAdapter(mAdapter);
                        Log.d("1-sze wczytanie", "To powinno byc tylko 1 raz/czyste");
                        wczytane = true;
                        dialog.dismiss();
                    }
                    @Override
                    public void failure(RetrofitError retrofitError) {
                        dialog.dismiss();
                    }
                });
            } else {
                service.listOrdersWithParameter(1, RepositoryClass.getInstance().getWyszukiwanieMiasta(), RepositoryClass.getInstance().getWyszukiwanieWojew(), RepositoryClass.getInstance().getWyszukiwanieKodowPoczt(), RepositoryClass.getInstance().getWyszukiwanieZamawNazwa(), new Callback<BaseListClass>() {
                    @Override
                    public void success(BaseListClass blc, Response response) {
                        RepositoryClass.getInstance().deleteDataObjectList();
                        RepositoryClass.getInstance().setBaseListClass(blc);
                        mAdapter = new MyAdapter(RepositoryClass.getInstance().getDataObjectList());
                        mRecyclerView.setAdapter(mAdapter);
                        Log.d("1-sze wczytanie", "To powinno byc tylko 1 raz/z param");
                        wczytane = true;
                        dialog.dismiss();
                        Log.d("Strona", "param n strona: " + strona);
                    }
                    @Override
                    public void failure(RetrofitError retrofitError) {
                        dialog.dismiss();
                    }
                });
            }
        }
    }
}