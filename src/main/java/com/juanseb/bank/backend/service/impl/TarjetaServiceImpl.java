package com.juanseb.bank.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Tarjeta;
import com.juanseb.bank.backend.repository.TarjetaRepository;
import com.juanseb.bank.backend.service.TarjetaService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TarjetaServiceImpl implements TarjetaService {
	
	@Autowired
	TarjetaRepository tarjetaRepository;

	@Transactional
    @Override
	public Tarjeta crearTarjeta(Tarjeta tarjetaNueva) {
		return tarjetaRepository.save(tarjetaNueva);
	}

	@Transactional
	@Override
	public List<Tarjeta> obtenerTarjetaByCuenta(Long cuentaId) {
		
		return tarjetaRepository.findByCuentaId(cuentaId);
	}

	@Transactional
	@Override
	public Tarjeta obtenerTarjetaByNumeroTarjeta(Long numeroTarjeta) {
		return tarjetaRepository.obtenerTarjetaByNumeroTarjeta(numeroTarjeta);
	}

	@Transactional
	@Override
	public Tarjeta obtenerTarjetaById(Long idTarjeta) throws EntityNotFoundException{
		return tarjetaRepository.findById(idTarjeta).orElseThrow(()
                -> new EntityNotFoundException("No se ha encontrado tarjeta con numero: "+idTarjeta));
	}

	
}
