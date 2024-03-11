package com.ti2cc;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.List;
import spark.Request;
import spark.Response;


public class CarroService {
	CarrosDAO CarrosDAO;
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_DESCRICAO = 2;
	private final int FORM_ORDERBY_PRECO = 3;
	
	
	public CarroService() {
		this.CarrosDAO = new CarrosDAO();
		makeForm();
	}
	
	public void makeForm() {
		makeForm(FORM_INSERT, new Carros(), FORM_ORDERBY_DESCRICAO);
	}
	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Carros(), orderBy);
	}
	
	public void makeForm(int tipo, Carros carro, int orderBy) {
		String nomeArquivo = "index.html";
		form = "";
		try {
			Scanner entrada = new Scanner(new File(nomeArquivo));
			while(entrada.hasNext()) {
				form += (entrada.nextLine() + "\n");
			}
			entrada.close();
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		String umProduto = "";
		if(tipo != FORM_INSERT) {
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/produto/list/1\">Novo Produto</a></b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t<br>";					
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/carro/";
			String name, descricao, buttonLabel;
			if(tipo == FORM_INSERT) {
				action += "insert";
				name = "Inserir Carro";
				descricao = "argo, uno, ...";
				buttonLabel = "Inserir";
			}else {
				action += "update/" + carro.getCodigo();
				name = "Atualizar Carro (ID" + carro.getCodigo() + ")";
				descricao = carro.getModelo();
				buttonLabel = "Atualizar";
			}
			umProduto += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ descricao +"\"></td>";
			umProduto += "\t\t\t<td>Preco: <input class=\"input--register\" type=\"text\" name=\"preco\" value=\""+ carro.getPreco() +"\"></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Data de fabricação: <input class=\"input--register\" type=\"text\" name=\"dataFabricacao\" value=\""+ carro.getAno().toString() + "\"></td>";
			umProduto += "\t\t\t<td>Data de validade: <input class=\"input--register\" type=\"text\" name=\"dataValidade\" value=\""+ carro.getModelo() + "\"></td>";
			umProduto += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";
			umProduto += "\t</form>";
		} else if(tipo == FORM_DETAIL) {
			umProduto += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Produto (Codigo " + carro.getCodigo() + ")</b></font></td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;Placa: "+ carro.getPlaca() +"</td>";
			umProduto += "\t\t\t<td>Marca: "+ carro.getMarca() +"</td>";
			umProduto += "\t\t\t<td>Modelo: "+ carro.getModelo() +"</td>";
			umProduto += "\t\t\t<td>Ano: "+ carro.getAno() +"</td>";
			umProduto += "\t\t\t<td>Preco: "+ carro.getPreco() +"</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t\t<tr>";
			umProduto += "\t\t\t<td>&nbsp;</td>";
			umProduto += "\t\t</tr>";
			umProduto += "\t</table>";				
		}else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-PRODUTO>", umProduto);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Produtos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_DESCRICAO + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_PRECO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Carros> produtos;
		if (orderBy == FORM_ORDERBY_ID) {                 	produtos = CarrosDAO.getOrderByCodigo();
		} else if (orderBy == FORM_ORDERBY_DESCRICAO) {		produtos = CarrosDAO.getOrderByMarca();
		} else if (orderBy == FORM_ORDERBY_PRECO) {			produtos = CarrosDAO.getOrderByPreco();
		} else {											produtos = CarrosDAO.get();
		}
		int i = 0;
		String bgcolor = "";
		for (Carros p : produtos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getCodigo() + "</td>\n" +
            		  "\t<td>" + p.getMarca() + "</td>\n" +
            		  "\t<td>" + p.getPreco() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/" + p.getCodigo() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/update/" + p.getCodigo() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteProduto('" + p.getCodigo() + "', '" + p.getMarca() + "', '" + p.getPreco() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);	
	}
	
	public Object insert(Request request, Response response) {
		Gson gson = new Gson();
		Carros produto = gson.fromJson(request.body(), Carros.class);
		
		int respI = 0;
		List<Carros> carrosL = CarrosDAO.getOrderByCodigo();
		
		for(Carros u: carrosL) {
			respI = u.getCodigo();
		}
		
		respI += 1;
		produto.setCodigo(respI);
		
		String resp = "";
		
		
		if(CarrosDAO.insert(produto) == true) {
            resp = "Produto inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Produto não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":codigo"));		
		Carros produto = (Carros) CarrosDAO.get(id);
		
		if (produto != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, produto, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Produto " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	public Object getToUpdate(Request request, Response response) {
	    int id = Integer.parseInt(request.params(":codigo"));        
	    Carros produto = (Carros) CarrosDAO.get(id);
	    
	    if (produto != null) {
	        response.status(200); // success
	        makeForm(FORM_UPDATE, produto, FORM_ORDERBY_DESCRICAO); // Preenche o formulário com os dados do produto
	    } else {
	        response.status(404); // 404 Not found
	        String resp = "Produto " + id + " não encontrado.";
	        makeForm();
	        form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
	    }

	    return form;
	}
	
	public Object getAll(Request request, Response response) {
	    // Obter a lista de todos os carros do banco de dados
	    List<Carros> carros = CarrosDAO.getOrderByCodigo();

	    if (carros.isEmpty()) {
	        System.out.println("A lista de carros está vazia.");
	    }

	    // Serializar a lista de carros para JSON usando Gson
	    Gson gson = new Gson();
	    String json = "";
	    try {
	        json = gson.toJson(carros);
	    } catch (Exception e) {
	        System.out.println("Erro ao serializar a lista de carros para JSON: " + e.getMessage());
	    }

	    // Definir o tipo de conteúdo da resposta como JSON
	    response.type("application/json");

	    // Retornar a lista de carros como JSON
	    return json;
	}
	public Object update(Request request, Response response) {
	    int id = Integer.parseInt(request.params(":codigo"));
	    String resp = "";       

	    // Verifica se o produto existe no banco de dados
	    Carros produto = CarrosDAO.get(id);
	    if (produto != null) {
	        Gson gson = new Gson();
	        try {
	            // Desserializar o JSON recebido para o objeto Carros
	            Carros novoProduto = gson.fromJson(request.body(), Carros.class);
	            // Configura o código do novo produto
	            novoProduto.setCodigo(id);
	            // Atualiza o produto no banco de dados
	            CarrosDAO.update(novoProduto);
	            response.status(200); // success
	            resp = "Produto (ID " + novoProduto.getCodigo() + ") atualizado!";
	        } catch (Exception e) {
	            // Lidar com qualquer exceção durante a desserialização
	            e.printStackTrace();
	            response.status(400); // Bad request
	            resp = "Erro ao atualizar o produto: " + e.getMessage();
	        }
	    } else {
	        response.status(404); // 404 Not found
	        resp = "Produto (ID " + id + ") não encontrado!";
	    }
	    makeForm();
	    return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}
	
	public Object delete(Request request, Response response) {
	    int id = Integer.parseInt(request.params(":codigo"));
	    Carros produto = CarrosDAO.get(id);
	    String resp = "";       

	    if (produto != null) {
	        CarrosDAO.delete(id);
	        response.status(200); // success
	        resp = "Produto (" + id + ") excluído!";
	    } else {
	        response.status(404); // 404 Not found
	        resp = "Produto (" + id + ") não encontrado!";
	    }

	    makeForm(); // Atualiza o formulário

	    // Substitui o marcador de posição <input type="hidden" id="msg" name="msg" value="">
	    // pela mensagem de resposta no formulário
	    form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");

	    return form;
	}

		
}
