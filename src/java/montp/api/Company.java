package montp.api;

import montp.data.model.GenericEntity;

import javax.annotation.ManagedBean;
import java.io.Serializable;

/**
 * The Company class defines the identity of a quoted
 * company
 */
public class Company extends GenericEntity implements Serializable {

    private String symbol;
    private String name;

    public Company() { }

    public Company(String name, String symbol) {
        this.symbol = symbol;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Company other = (Company) obj;
        if (this.symbol != other.symbol && (this.symbol == null || !this.symbol.equals(other.symbol))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.symbol != null ? this.symbol.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return name;
    }



}