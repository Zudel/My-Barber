package engineering.container;

import model.Saloon;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SaloonCatalogue {

    private List<Saloon> saloonList;

    public SaloonCatalogue() {

        this.saloonList = new ArrayList<>();

    }

    public SaloonCatalogue(List<Saloon> saloonList) {

        this.saloonList = saloonList;

    }

    public void setSaloonList(List<Saloon> saloons){

        this.saloonList = saloons;

    }

    public List<Saloon> getSaloonList(){

        return this.saloonList;

    }

    public void addSaloon(Saloon saloon) {

        saloonList.add(saloon);

    }


    @Nullable
    public Saloon getSaloonByName (String saloonName) {

        for(Saloon saloon : this.saloonList) {
            if(saloon.getName().compareTo(saloonName) == 0) {
                return saloon;
            }
        }

        return null;

    }


    public boolean removeSaloon (String saloonName) {

        for (Saloon saloon : saloonList) {
            if(saloon.getName().compareTo(saloonName) == 0) {
                saloonList.remove(saloon);
                return true;
            }

        }

        return false;

    }

}