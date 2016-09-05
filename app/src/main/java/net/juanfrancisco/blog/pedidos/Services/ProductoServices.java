package net.juanfrancisco.blog.pedidos.Services;

import net.juanfrancisco.blog.pedidos.Models.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franc on 3/09/2016.
 */
public class ProductoServices
{


    String count;
    String next ;
    String previous ;
    ArrayList<Producto> results;




    public boolean valid()
    {
        return results.size()>0;
    }


    @Override
    public String toString() {
        return "ProductoServices<" +
                "count='" + count + '\'' +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results.toString() +
                '>';
    }




    public ProductoServices() {
    }


    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public ArrayList<Producto> getResults() {
        return results;
    }

    public void setResults(ArrayList<Producto> results)
    {
        this.results = results;
    }



}
