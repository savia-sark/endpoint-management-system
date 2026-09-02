package com.example.iaccs;

import com.example.iaccs.dto.EndpointRequestDTO;
import com.example.iaccs.entity.*;
import com.example.iaccs.entity.enums.EndpointType;
import com.example.iaccs.entity.enums.Status;
import com.example.iaccs.repository.*;
import com.example.iaccs.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class IaccsApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(IaccsApplication.class, args);

		CommandService commandService=context.getBean(CommandService.class);
		NtnService ntnservice=context.getBean(NtnService.class);
		AgencyTypeService agencyTypeService=context.getBean(AgencyTypeService.class);
		AgencyService agencyService=context.getBean(AgencyService.class);
		EndpointService endpointService=context.getBean(EndpointService.class);

		//EndpointRequestDTO requestDTO=new EndpointRequestDTO();
		//requestDTO.setEndpointId("AGE1-OWS8");
		//requestDTO.setEndpointType(EndpointType.OWS);
		//requestDTO.setStatus(Status.Unserviceable);
		//requestDTO.setRemarks(null);
		//requestDTO.setSystemOwner("test");
		//requestDTO.setImRaised("N/A");
		//requestDTO.setAgencyId(1);
		//endpointService.createEndpoint(requestDTO);

//	    System.out.println(endpointService.getAllEndpoints());
//		System.out.println(ntnservice.getAllNtns());
//		System.out.println(agencyService.getAllAgencies());
//		Endpoint endpoint=new Endpoint();
//		endpoint.setEndpointId("temp");
//		endpoint.setEndpointType("ows");
//		endpoint.setStatus("serviceable");
//		endpoint.setRemarks("ok");
//		endpoint.setSystemOwner("user1");
//		endpoint.setImRaised("t1");
//		Agency agency=agencyService.getAgencyByName("Agency8");
//		endpoint.setAgency(agency);
//
//		System.out.println(agencyService.getAllAgencies());
//
//		Agency agency1=new Agency();
//		endpoint.setEndpointId("t4");
//		endpoint.setEndpointType("TC");
//		endpoint.setStatus("UnServiceable");
//		endpoint.setRemarks("not");
//		endpoint.setSystemOwner("user1");
//		endpoint.setImRaised("t1");
//		endpoint.setAgency(agency);
//
//
//
//		System.out.println(agencyService.getAllAgencies());
//
//		endpointService.deleteEndpoint("t4");
//		System.out.println(agencyService.getAllAgencies());

	}

}
