package com.ability.rest;

import com.ability.data.DescriptorRepository;
import com.ability.data.VineRepository;
import com.ability.imaging.features2D.ImageFeatures2D;
import com.ability.model.Descriptor;
import com.ability.model.Vine;
import com.ability.model.VineDescribed;
import com.ability.service.ParallelThread;
import com.ability.service.ServiceDescriptor;
import com.ability.service.ServiceVin;
import com.ability.service.ServiceVineDescribed;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.enterprise.context.RequestScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by ikizema on 15-08-31.
 */
@Path("/vines")
@RequestScoped
public class RestVine {
    @Inject
    private Logger logger;

    @Inject
    private VineRepository vineRepository;

    @Inject
    private DescriptorRepository descriptorRepository;

    @Inject
    ServiceVin serviceVin;

    @Inject
    ServiceDescriptor serviceDescriptor;

    @Inject
    ServiceVineDescribed serviceVineDescribed;

//    @Resource
//    ManagedThreadFactory factory;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vine> listAllVines() {
        return vineRepository.findAllOrderedById();
    }

    @GET
    @Path("/process")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Descriptor> processAllVines() {
        List<Vine> vines = vineRepository.findAllNotDescribedOrderedById();
        for (int i=0; i<vines.size(); i++) {
            try {
                Descriptor descriptor = new Descriptor(vines.get(i));
                serviceDescriptor.save(descriptor);
                serviceVineDescribed.addVineDescribed(new VineDescribed(descriptor));
                logger.info("ServiceVineDescribed contains "+serviceVineDescribed.getSize()+" units.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return descriptorRepository.findAllOrderedByVineID();
    }

    @GET
    @Path("/vinesDescribed")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vine> getAllVineDescribed() {
        logger.info("ServiceVineDescribed contains "+serviceVineDescribed.getSize()+" units.");
        List<VineDescribed> vinesDescribed = serviceVineDescribed.getVineDescribeds();
        List<Vine> vines =  new ArrayList<Vine>();
        if (vinesDescribed == null) {
            return null;
        }
        for (int i =0; i < vinesDescribed.size(); i++) {
            vines.add(vinesDescribed.get(i).getVine());
        }
        return vines;
    }

    @POST
    @Path("/findVine")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Vine findVineFromImage(InputStream uploadedInputStream) {
        try {
            return serviceVineDescribed.findVineFromImage(ImageIO.read(uploadedInputStream));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @POST
    @Path("/findVineParallel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Vine findVineParallelFromImage(InputStream uploadedInputStream) {
//        Thread thread = null;
//        try {
//            thread = factory.newThread(new ParallelThread(1 , new ImageFeatures2D(ImageIO.read(uploadedInputStream), false), serviceVineDescribed.getVineDescribeds()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        thread.start();

        try {
            //Get the default ManagedThreadFactory implementation.
            InitialContext ctx = new InitialContext();
            ManagedThreadFactory factory =
                    (ManagedThreadFactory) ctx.lookup("java:comp/DefaultManagedThreadFactory");

            //Create a new thread using the thread factory created above.
            Thread myThread = factory.newThread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Running a task using Managed thread ...");
                }
            });

            //Start executing the thread.
            myThread.start();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response importVine(Vine vine) {
        Response.ResponseBuilder builder = null;

        try {
            // Verify and save
            serviceVin.save(vine);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        logger.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

}
