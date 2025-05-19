
package javafinalproject;

public class Supplier extends User {
      private int contactInfo;
    private int supplierProduct;
    private double totalRevenue;

    public Supplier( String name, String email, String password,String description, int contactInfo) {
        super(name,email,password,description);
        this.contactInfo = contactInfo;
        this.supplierProduct = 0;
        this.totalRevenue = 0.0;
    }

   
    public int getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(int contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getSupplierProduct() {
        return supplierProduct;
    }

    public void setSupplierProduct(int supplierProduct) {
        this.supplierProduct = supplierProduct;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    @Override
    public String toString() {
        return "Supplier{id=" + getId() 
                + ", name=" + getName()
                + ", contactInfo=" + contactInfo 
                + ", supplierProduct=" + supplierProduct
                + ", Revenue=" + totalRevenue 
                + "}";
    
    
    
    
    
}

}