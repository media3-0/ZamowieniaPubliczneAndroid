package pl.media30.zamowieniapubliczne.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.squareup.otto.Subscribe;

import pl.media30.zamowieniapubliczne.ResultBus;
import pl.media30.zamowieniapubliczne.ResultEvent;
import pl.media30.zamowieniapubliczne.Adapters.MainAdapter;
import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import pl.media30.zamowieniapubliczne.MojePanstwoService;
import pl.media30.zamowieniapubliczne.R;
import pl.media30.zamowieniapubliczne.RepositoryClass;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Fragment odpowiedzialny za wyświetlanie danych w UltimateRecycleView.
 */
public class MainActivityFragment extends Fragment {

    UltimateRecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MainAdapter mAdapter;
    public int strona;// = 2;
    Bundle bundle = new Bundle();
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    public String query="";
    public boolean nowePob = false;

    public MainActivityFragment() {
    }

    private Object mActivityResultSubscriber = new Object() {
        @Subscribe
        public void onActivityResultReceived(ResultEvent event) {
            int requestCode = event.getRequestCode();
            int resultCode = event.getResultCode();
            Intent data = event.getData();
            onActivityResult(requestCode, resultCode, data);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Metoda", "onActResult");

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

                parametr = data.getStringExtra("wartoscREGON");
                if (parametr.equals("*"))
                    parametr = null;
                bundle.putString("wartoscREGON", parametr);
                RepositoryClass.getInstance().setWyszukiwanieZamawREGON(parametr);

                parametr = data.getStringExtra("wartoscWWW");
                if (parametr.equals("*"))
                    parametr = null;
                bundle.putString("wartoscWWW", parametr);
                RepositoryClass.getInstance().setWyszukiwanieZamawWWW(parametr);

                parametr = data.getStringExtra("wartoscEmail");
                if (parametr.equals("*"))
                    parametr = null;
                bundle.putString("wartoscEmail", parametr);
                RepositoryClass.getInstance().setWyszukiwanieZamawEmail(parametr);

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
                RepositoryClass.getInstance().setWyszukiwanieZamawREGON(null);
                RepositoryClass.getInstance().setWyszukiwanieZamawWWW(null);
                RepositoryClass.getInstance().setWyszukiwanieZamawEmail(null);
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
            bundle.putInt("getPos", 0);
        }
        try {
            if (savedInstanceState.getString("wartoscMiasto").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieMiasta(null);
            if (savedInstanceState.getString("wartoscWoj").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieMiasta(null);
            if (savedInstanceState.getString("wartoscKod").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieKodowPoczt(null);
            if (savedInstanceState.getString("wartoscNazwa").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieZamawNazwa(null);
            if (savedInstanceState.getString("wartoscREGON").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieZamawREGON(null);
            if (savedInstanceState.getString("wartoscWWW").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieZamawWWW(null);
            if (savedInstanceState.getString("wartoscEmail").equals("*"))
                RepositoryClass.getInstance().setWyszukiwanieZamawEmail(null);
            bundle.putBoolean("getWczytaj", false);


        } catch (Exception e) {
        }
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mLayoutManager.scrollToPosition(bundle.getInt("getPos"));
        Log.d("Metoda", "onActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        ResultBus.getInstance().unregister(mActivityResultSubscriber);
        bundle.putInt("getPos", mLayoutManager.findFirstVisibleItemPosition());
        Log.d("Metoda", "onStop");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            String strtext = getArguments().getString("query");
        } catch (Exception e) {
        }
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        ResultBus.getInstance().register(mActivityResultSubscriber);

        if (query.length() >= 1 && !query.equals("*")) {
            if (nowePob==true)
                bundle.putBoolean("getWczytaj", false);
        } else if (query.toString().equals("*")) {
            if (nowePob==true)
                bundle.putBoolean("getWczytaj", false);
        }
        nowePob=false;

        mRecyclerView = (UltimateRecyclerView) getView().findViewById(R.id.ultimate_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        if (bundle.getInt("getPos") == -1)
            bundle.putInt("getPos", 0);
        mLayoutManager.scrollToPosition(bundle.getInt("getPos"));
        Log.d("Metoda", "onStart");

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = mLayoutManager.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();
                int wszystkieElem = RepositoryClass.getInstance().getBaseListClass().searchClass.paginationClass.total;
                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount && (totalItemCount != wszystkieElem)) {
                        loading = false;
                        wczytajDane();
                        Log.d("LOADMORE", strona + "");
                    }
                }
            }
        });

        if (bundle.getBoolean("getWczytaj") == false){
            wczytajDane();
            bundle.putBoolean("getWczytaj", true);
        }
    }

    void wczytajDane() {

        final RelativeLayout mainRelativeLayout = (RelativeLayout) getActivity().findViewById(R.id.UltimateRecycleViewRelativeLayout);
        final RelativeLayout wheelRelativeLayout = (RelativeLayout) getActivity().findViewById(R.id.WheelRelativeLayout);
        if(strona<2){
            mainRelativeLayout.setVisibility(View.GONE);
            wheelRelativeLayout.setVisibility(View.VISIBLE);
        }

        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.mojepanstwo.pl/dane/")
                .build();
        final MojePanstwoService service = restAdapter.create(MojePanstwoService.class);
        Log.d("Metoda","wczytajdane");
        if ( bundle.getBoolean("getWczytaj")==false) {
            strona = 1;
            bundle.putBoolean("getWczytaj", true);
            if (RepositoryClass.getInstance().getDataObjectList() != null)
                RepositoryClass.getInstance().deleteDataObjectList();
        }
        try {
            bundle.putInt("getPos", mLayoutManager.findFirstCompletelyVisibleItemPosition());
        } catch (Exception e) {
            bundle.putInt("getPos", 0);
        }

        if ((RepositoryClass.getInstance().getWyszukiwanieMiasta() == null) && (RepositoryClass.getInstance().getWyszukiwanieWojew() == null) && (RepositoryClass.getInstance().getWyszukiwanieKodowPoczt() == null) && (RepositoryClass.getInstance().getWyszukiwanieZamawNazwa() == null) && (RepositoryClass.getInstance().getWyszukiwanieZamawREGON() == null) && (RepositoryClass.getInstance().getWyszukiwanieZamawWWW() == null) && (RepositoryClass.getInstance().getWyszukiwanieZamawEmail() == null) && (RepositoryClass.getInstance().getGlowneZapyt() == null)) {
            service.listOrders(strona, new Callback<BaseListClass>() {
                @Override
                public void success(BaseListClass blc, Response response) {
                    RepositoryClass.getInstance().setBaseListClass(blc);
                    if (strona<2) {
                        try {
                            Toast.makeText(getActivity(), "Znaleziono " + RepositoryClass.getInstance().getCount() + " elementów", Toast.LENGTH_SHORT).show();
                        } catch (NullPointerException e) {
                        }
                    }
                    mAdapter = new MainAdapter(RepositoryClass.getInstance().getDataObjectList());
                    mRecyclerView.setAdapter(mAdapter);

                    mLayoutManager.scrollToPosition(bundle.getInt("getPos"));
                    Log.d("Strona", " bez param strona loadmore: " + strona);
                    strona++;
                    loading = true;
                    wheelRelativeLayout.setVisibility(View.GONE);
                    mainRelativeLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    //progressWheel.stopSpinning();
                    wheelRelativeLayout.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Błąd. Sprawdź połączenie z internetem", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            service.listOrdersWithParameter(strona, RepositoryClass.getInstance().getWyszukiwanieMiasta(), RepositoryClass.getInstance().getWyszukiwanieWojew(), RepositoryClass.getInstance().getWyszukiwanieKodowPoczt(), RepositoryClass.getInstance().getWyszukiwanieZamawNazwa(), RepositoryClass.getInstance().getWyszukiwanieZamawREGON(), RepositoryClass.getInstance().getWyszukiwanieZamawWWW(), RepositoryClass.getInstance().getWyszukiwanieZamawEmail(), RepositoryClass.getInstance().getGlowneZapyt(), new Callback<BaseListClass>() {
                @Override
                public void success(BaseListClass blc, Response response) {
                    RepositoryClass.getInstance().setBaseListClass(blc);
                    if (strona<2) {
                        try {
                            Toast.makeText(getActivity(), "Znaleziono " + RepositoryClass.getInstance().getCount() + " elementów", Toast.LENGTH_SHORT).show();
                        } catch (NullPointerException e) {}
                    }
                    mAdapter = new MainAdapter(RepositoryClass.getInstance().getDataObjectList());
                    mRecyclerView.setAdapter(mAdapter);
                    mLayoutManager.scrollToPosition(bundle.getInt("getPos"));
                    Log.d("Strona", "param strona loadmore: " + strona);
                    strona++;
                    loading = true;
                    wheelRelativeLayout.setVisibility(View.GONE);
                    mainRelativeLayout.setVisibility(View.VISIBLE);
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    wheelRelativeLayout.setVisibility(View.GONE);
                }
            });
        }
    }
}