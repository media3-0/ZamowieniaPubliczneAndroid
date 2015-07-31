package pl.media30.zamowieniapubliczne;

import java.util.List;
import pl.media30.zamowieniapubliczne.Models.DownloadList.BaseListClass;
import pl.media30.zamowieniapubliczne.Models.DownloadList.DataObjectClass;
import pl.media30.zamowieniapubliczne.Models.SingleElement.BaseClass;


/**
 * Created by Adrian on 2015-07-21.
 */
public class RepositoryClass {

    private static RepositoryClass mInstance = null;
    private List<DataObjectClass> dataObjectList;
    private BaseListClass baseListClass;
    private BaseClass baseClass;
    private String wyszukiwanieMiasta=null;
    private String wyszukiwanieWojew=null;
    private String wyszukiwanieKodowPoczt = null;
    private String wyszukiwanieZamawNazwa = null;
    private String wyszukiwanieZamawREGON = null;
    private String wyszukiwanieZamawWWW = null;
    private String wyszukiwanieZamawEmail = null;
    private String glowneZapyt = null;

    public boolean pobierzJedenRaz = true;

    public void setBaseListClass(BaseListClass baseListClass) {
        this.baseListClass = baseListClass;
            if (dataObjectList == null){
                dataObjectList = baseListClass.getSearchClass().getDataObjectClass();
            }else{
                for(int i=0;i<baseListClass.getSearchClass().getDataObjectClass().size();i++) {
                    dataObjectList.add(baseListClass.getSearchClass().getDataObjectClass().get(i));
                }
            }
    }

    public int getCount(){
        return baseListClass.getSearchClass().paginationClass.total;
    }

    public BaseListClass getBaseListClass() {
        return baseListClass;
    }

    public void setBaseClass(BaseClass baseClass) {
        this.baseClass = baseClass;
    }
    public BaseClass getBaseClass(){
        return baseClass;
    }

    public List<DataObjectClass> getDataObjectList() {
        return dataObjectList;
    }
    public void setDataObjectList(List<DataObjectClass> dataObjectList){
        this.dataObjectList=dataObjectList;
    }

    public void deleteDataObjectList(){
        dataObjectList.clear();
    }

    public void setWyszukiwanieMiasta(String wyszukiwanieMiasta){
        this.wyszukiwanieMiasta=wyszukiwanieMiasta;
    }
    public String getWyszukiwanieMiasta(){
        return wyszukiwanieMiasta;
    }

    public void setWyszukiwanieWojew(String wyszukiwanieWojew){
        this.wyszukiwanieWojew=wyszukiwanieWojew;
    }
    public String getWyszukiwanieWojew(){
        return wyszukiwanieWojew;
    }

    public void setWyszukiwanieKodowPoczt(String wyszukiwanieKodowPoczt){
        this.wyszukiwanieKodowPoczt=wyszukiwanieKodowPoczt;
    }
    public String getWyszukiwanieKodowPoczt(){
        return wyszukiwanieKodowPoczt;
    }

    public void setWyszukiwanieZamawNazwa(String wyszukiwanieZamawNazwa){
        this.wyszukiwanieZamawNazwa=wyszukiwanieZamawNazwa;
    }
    public String getWyszukiwanieZamawNazwa(){
        return wyszukiwanieZamawNazwa;
    }

    public void setWyszukiwanieZamawREGON(String wyszukiwanieZamawREGON){
        this.wyszukiwanieZamawREGON=wyszukiwanieZamawREGON;
    }
    public String getWyszukiwanieZamawREGON(){
        return wyszukiwanieZamawREGON;
    }

    public void setWyszukiwanieZamawWWW(String wyszukiwanieZamawWWW){
        this.wyszukiwanieZamawWWW=wyszukiwanieZamawWWW;
    }
    public String getWyszukiwanieZamawWWW(){
        return wyszukiwanieZamawWWW;
    }

    public void setGlowneZapyt(String glowneZapyt){
        this.glowneZapyt=glowneZapyt;
    }
    public String getGlowneZapyt(){
        return glowneZapyt;
    }

    public void setWyszukiwanieZamawEmail(String wyszukiwanieZamawEmail){
        this.wyszukiwanieZamawEmail=wyszukiwanieZamawEmail;
    }
    public String getWyszukiwanieZamawEmail(){
        return wyszukiwanieZamawEmail;
    }

    private RepositoryClass() {
    }

    public static RepositoryClass getInstance() {
        if (mInstance == null) {
            mInstance = new RepositoryClass();
        }
        return mInstance;
    }
}



