package com.ti2cc;

import java.sql.*;
import java.security.*;
import java.math.*;

public class DAO {
	protected Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String serverName = "localhost";
		String mydatabase = "Crud";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "ti2cc";
		String password = "258789";
		boolean status = false;
		
		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
			
		}catch(ClassNotFoundException e) {
			System.err.println("Conexão NÃO efetuada com postgres!");
		}catch(SQLException e) {
			System.err.println("Conexão NÃO efetuada com postgres!");
		}
		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try {
			conexao.close();
			status = true;
		}catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
		
	}
	
	public static String toMD5(String senha) throws Exception{
		MessageDigest m= MessageDigest.getInstance("MD5");
		m.update(senha.getBytes(), 0, senha.length());
		return new BigInteger(1,m.digest()).toString(16);
	}
	
	
}
