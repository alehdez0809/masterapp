package Controller;

/**
 *
 * @author Riquelme
 */
public class Usuario {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    private String nombre, apellidoP, apellidoM, correo, contra, tel, del;
    private int tipU;

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public void setTipU(int tipU) {
        this.tipU = tipU;
    }

    public String getTel() {
        return tel;
    }

    public String getDel() {
        return del;
    }

    public int getTipU() {
        return tipU;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContra() {
        return contra;
    }
}
