package com.DAD.MercadoCentral.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.DAD.MercadoCentral.extras.Category;
import com.DAD.MercadoCentral.models.ProductModel;
import com.DAD.MercadoCentral.service.ClientService;
import com.DAD.MercadoCentral.service.ProductService;
import com.DAD.MercadoCentral.service.WorkerService;

import ch.qos.logback.core.net.server.Client;

@Controller
public class MiscelaneousController {

    @Autowired
    ClientService cs;
    @Autowired
    WorkerService ws;
    @Autowired
    ProductService ps;

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Foto controller
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/download_image/product/{id}")
    public ResponseEntity<Object> downloadImageProduct(Model model, @PathVariable long id) {

        ProductModel product = ps.getProdById(id);

        if (product.getImage() != null) {

            Resource image;
            try {
                image = new InputStreamResource(product.getImage().getBinaryStream());
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .contentLength(product.getImage().length())
                        .body(image);
            } catch (SQLException e) {
                e.printStackTrace();
                return ResponseEntity.notFound().build();
            }

        } else {
            // e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/download_image/category/{id}")
    public ResponseEntity<Object> downloadImageCategory(Model model, @PathVariable long id) {

        try {
            Resource image = new InputStreamResource(
                    Category.categoryList().get((int) id).getPhoto().getBinaryStream());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                    .contentLength(Category.categoryList().get((int) id).getPhoto().length())
                    .body(image);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }

    }

}