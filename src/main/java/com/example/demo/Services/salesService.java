package com.example.demo.Services;

import com.example.demo.Entities.sales;
import com.example.demo.Entities.sales_details;
import com.example.demo.Repositories.*;
import com.example.demo.Controllers.LoggingController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Calendar;


@Service
public class salesService {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @Autowired
    salesRepository salesRepository;

    @Autowired
    sales_detailsRepository detailsRepository;

    @Autowired
    sellersRepository sellersRepository;

    @Autowired
    clientsRepository clientsRepository;

    @Autowired
    productsRepository productsRepository;

    @Transactional
    public sales createSale(sales sale) {
        sale.setCreation_date(Calendar.getInstance().getTime());

        double total = 0;
        for (sales_details s : sale.getDetailsList()) {
            //if the price is specified in the api
            if(s.getPrice()!=0) {
                total += s.getPrice() * s.getQuantity();
            }
            else //get the price of the product from the database
            {
                total += productsRepository.findById(s.getProduct().getId()).getPrice() * s.getQuantity();
            }
        }
        sale.setTotal(total);
        sale.setClient(clientsRepository.findById(sale.getClient().getId()));
        sales createdSale = salesRepository.save(sale);

        ArrayList<sales_details> createdSalesDetails = new ArrayList<>();
        for (sales_details s : createdSale.getDetailsList()) {
            s.setSale(new sales());
            s.getSale().setId(createdSale.getId());
            s.setProduct(productsRepository.findById(s.getProduct().getId()));
            s.setSeller(s.getProduct().getSeller());
            if(s.getPrice()==0)
            {
                s.setPrice(productsRepository.findById(s.getProduct().getId()).getPrice());
            }
            createdSalesDetails.add(detailsRepository.save(s));
        }

        createdSale.setDetailsList(createdSalesDetails);
        return createdSale;
    }

    //it is used to edit quantities or prices or both at the same time
    @Transactional
    public sales updateSale(int sale_id, ArrayList<sales_details> detailsList) {
        sales updatedSale=null;
        for(sales_details details:detailsList){
        sales_details updatedSalesDetails = detailsRepository.findByProduct_idAndSale_id(details.getProduct().getId(), sale_id);

        double prev_total = updatedSalesDetails.getPrice() * updatedSalesDetails.getQuantity();

        logger.info("sales_details update");
        if (details.getPrice() != 0) {
            logger.info("sales_details with id " + updatedSalesDetails.getId() + " price was " + updatedSalesDetails.getPrice());
            updatedSalesDetails.setPrice(details.getPrice());
            logger.info("sales_details with id " + updatedSalesDetails.getId() + " price now " + updatedSalesDetails.getPrice());
        }

        if (details.getQuantity() != 0) {
            logger.info("sales_details with id " + updatedSalesDetails.getId() + " quantity was " + updatedSalesDetails.getQuantity());
            updatedSalesDetails.setQuantity(details.getQuantity());
            logger.info("sales_details with id " + updatedSalesDetails.getId() + " quantity now " + updatedSalesDetails.getQuantity());
        }


        double cur_total = updatedSalesDetails.getPrice() * updatedSalesDetails.getQuantity();

        detailsRepository.save(updatedSalesDetails);

            updatedSale = salesRepository.findById(sale_id);
            logger.info("sale update");
            logger.info("sale with id "+updatedSale.getId()+" total was "+updatedSale.getTotal());
            updatedSale.setTotal(updatedSale.getTotal() - prev_total + cur_total);
            logger.info("sale with id "+updatedSale.getId()+" total now "+updatedSale.getTotal());
    }


        ArrayList<sales_details> updatedDetails = detailsRepository.findAllBySale_id(sale_id);

        updatedSale.setDetailsList(new ArrayList<>());

        //this loop to avoid sale_id so it doesn't causes stack over flow
        for (int i = 0; i < updatedDetails.size(); i++) {
            updatedSale.getDetailsList().add(new sales_details());
            updatedSale.getDetailsList().get(i).setId(updatedDetails.get(i).getId());
            updatedSale.getDetailsList().get(i).setSeller(updatedDetails.get(i).getSeller());
            updatedSale.getDetailsList().get(i).setProduct(updatedDetails.get(i).getProduct());
            updatedSale.getDetailsList().get(i).setPrice(updatedDetails.get(i).getPrice());
            updatedSale.getDetailsList().get(i).setQuantity(updatedDetails.get(i).getQuantity());
        }

        return salesRepository.save(updatedSale);
    }

    public ArrayList<sales> getAllSales() {
        ArrayList<sales> existedSales = salesRepository.findAll();
        for (sales s : existedSales) {
            ArrayList<sales_details> saleDetails = detailsRepository.findAllBySale_id(s.getId());
            s.setDetailsList(new ArrayList<>());
            for (int i = 0; i < saleDetails.size(); i++) {
                s.getDetailsList().add(new sales_details());
                s.getDetailsList().get(i).setId(saleDetails.get(i).getId());
                s.getDetailsList().get(i).setSeller(saleDetails.get(i).getSeller());
                s.getDetailsList().get(i).setProduct(saleDetails.get(i).getProduct());
                s.getDetailsList().get(i).setPrice(saleDetails.get(i).getPrice());
                s.getDetailsList().get(i).setQuantity(saleDetails.get(i).getQuantity());
            }
        }
        return existedSales;
    }

    public boolean isExists(int id) {
        return salesRepository.existsById(id);
    }

    public boolean isEmpty() {
        return salesRepository.findAll().size() == 0;
    }

    public boolean isBelong(int sale_id, sales_details details)
    {
        sales_details s = detailsRepository.findByProduct_idAndSale_id(details.getProduct().getId(), sale_id);
        return s!=null;
    }
}
