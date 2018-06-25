package farmacia.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ClientePJ extends Cliente {
	@Column(unique = true)
	private String cnpj;
	
	private String razao_social, inscricao_est;
	
	// getters e setters
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazao_social() {
		return razao_social;
	}
	
	public void setRazao_social(String razao_social) {
		this.razao_social = razao_social;
	}
	
	public String getInscricao_est() {
		return inscricao_est;
	}
	
	public void setInscricao_est(String inscricao_est) {
		this.inscricao_est = inscricao_est;
	}
}
