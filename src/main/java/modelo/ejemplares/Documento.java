
package modelo.ejemplares;

import modelo.Ejemplar;

public class Documento extends Ejemplar {
    private String tipoDocumentoDetalle;

    public Documento() {
        setTipoDocumento(TipoDocumento.Documento);
    }

    public Documento(Integer idEjemplar,String codigoEjemplar, String titulo, String autor, String ubicacion,
                     Estado estado, String tipoDocumentoDetalle) {
        super(idEjemplar,  codigoEjemplar,titulo, autor, ubicacion, TipoDocumento.Documento, estado);
        this.tipoDocumentoDetalle = tipoDocumentoDetalle;
    }

    public String getTipoDocumentoDetalle() { return tipoDocumentoDetalle; }
    public void setTipoDocumentoDetalle(String tipoDocumentoDetalle) { this.tipoDocumentoDetalle = tipoDocumentoDetalle; }
}
