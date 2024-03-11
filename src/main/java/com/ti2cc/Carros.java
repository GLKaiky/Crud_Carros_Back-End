package com.ti2cc;

public class Carros {
	private int codigo;
	private String placa;
	private String marca;
	private String modelo;
	private String ano;
	private double preco;
	
	//Construtores
	public Carros() {
		this.codigo = -1;
		this.placa = "";
		this.marca = "";
		this.modelo = "";
		this.ano = "2024";
		this.preco = 0.0;
	}
	
	public Carros(int codigo, String placa, String marca, String modelo, String ano, double preco) {
		this.codigo = codigo;
		this.placa = placa;
		this.marca = marca;
		this.modelo = modelo;
		this.ano = ano;
		this.preco = preco;
	}
	
	//get
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public String getPlaca() {
		return this.placa;
	}
	
	public String getMarca() {
		return this.marca;
	}
	
	public String getModelo() {
		return this.modelo;
	}
	
	public String getAno() {
		return this.ano;
	}
	
	public double getPreco() {
		return this.preco;
	}
	
	//set
	
	public void setCodigo(int Codigo) {
		this.codigo = Codigo;
	}
	
	public void setPlaca(String Placa) {
		
		this.placa = Placa;
	}
	
	public void setMarca(String Marca) {
		this.marca = Marca;
	}
	
	public void setModelo(String Modelo) {
		this.modelo = Modelo;
	}
	
	public void setAno(String Ano) {
		this.ano = Ano;
	}
	
	public void setPreco(double Preco) {
		this.preco = Preco;
	}
	
	@Override
	public String toString() {
		return "Carros [codigo ="+ codigo + ", placa=" + placa + ", marca=" + marca + ", modelo=" + modelo + ", ano=" + ano + ", preco=" + preco +"]";
	}
}
