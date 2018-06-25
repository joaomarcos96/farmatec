package farmacia.modelo;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Venda {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Double valor;
	
	@Temporal(TemporalType.DATE)
	private Calendar data = Calendar.getInstance();
	
	@ManyToOne
	private Funcionario funcionario;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy = "venda", fetch = FetchType.LAZY)
	private List<ProdutoVenda> itens = new LinkedList<ProdutoVenda>();
	
	public void add(ProdutoVenda item){
		this.itens.add(item);
	}
	
	// getters e setters	
	public void setValorTotal(){
		this.valor = 0.0;
		
		for (ProdutoVenda item : this.itens) {
			this.valor = this.valor + (item.getQuantidade() * item.getPreco());
		}
	}
	
	public Double getValorTotal(){
		setValorTotal();
		return valor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public List<ProdutoVenda> getItens() {
		return itens;
	}

	public void setItens(List<ProdutoVenda> itens) {
		this.itens = itens;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}	
	
}
