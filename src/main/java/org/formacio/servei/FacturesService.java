package org.formacio.servei;

import javax.transaction.Transactional;

import org.formacio.domain.Factura;
import org.formacio.domain.LiniaFactura;
import org.formacio.repositori.FacturesRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FacturesService {

	@Autowired
	FacturesRepositori repositoriFactures;
	
	@Autowired
	FidalitzacioService service;
	
	/*
	 * Aquest metode ha de carregar la factura amb id idFactura i afegir una nova linia amb les dades
	 * passades (producte i totalProducte)
	 * 
	 * S'ha de retornar la factura modificada
	 * 
	 * Per implementar aquest metode necessitareu una referencia (dependencia) a FacturesRepositori
	 */
	public Factura afegirProducte (long idFactura, String producte, int totalProducte) {
		Factura facturaCarregada = repositoriFactures.findById(idFactura).orElse(new Factura());
		LiniaFactura linia = new LiniaFactura();
		linia.setProducte(producte);
		linia.setTotal(totalProducte);
		facturaCarregada.getLinies().add(linia);
		repositoriFactures.save(facturaCarregada);
		
		notificaRegal(facturaCarregada);
		return facturaCarregada;
	}
	
	public void notificaRegal (Factura factura) {
		if (factura.getLinies().size() == 4) {
			service.notificaRegal(factura.getClient().getEmail());
		}
	}
}
