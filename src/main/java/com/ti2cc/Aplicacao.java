package com.ti2cc;

import static spark.Spark.*;

public class Aplicacao {
	private static CarroService produtoService = new CarroService();
	
    public static void main(String[] args) {
        port(8080);
         
        staticFiles.location("/public");
        

        
        post("/produto/insert", (request, response) -> produtoService.insert(request, response));

        get("/produto/:codigo", (request, response) -> produtoService.get(request, response));
        
        get("/produto/list/:orderby", (request, response) -> produtoService.getAll(request, response));

        get("/produto/update/:codigo", (request, response) -> produtoService.getToUpdate(request, response));
        
        post("/produto/update/:codigo", (request, response) -> produtoService.update(request, response));
           
        get("/produto/delete/:codigo", (request, response) -> produtoService.delete(request, response));
    }
}
