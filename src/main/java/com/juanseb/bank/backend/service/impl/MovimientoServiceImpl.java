package com.juanseb.bank.backend.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.juanseb.bank.backend.model.Categoria;
import com.juanseb.bank.backend.model.Cuenta;
import com.juanseb.bank.backend.model.Movimiento;
import com.juanseb.bank.backend.model.Tarjeta;
import com.juanseb.bank.backend.model.enumerado.TipoMovimiento;
import com.juanseb.bank.backend.payload.filter.MovimientoMesFilter;
import com.juanseb.bank.backend.payload.filter.MovimientosFilter;
import com.juanseb.bank.backend.repository.CategoriaRepository;
import com.juanseb.bank.backend.repository.CuentaRepository;
import com.juanseb.bank.backend.repository.MovimientoRepository;
import com.juanseb.bank.backend.repository.TarjetaRepository;
import com.juanseb.bank.backend.service.MovimientoService;

@Service
public class MovimientoServiceImpl implements MovimientoService {
	@Autowired
	MovimientoRepository movimientoRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@Autowired
	CuentaRepository cuentaRepository;
	
	@Autowired
	TarjetaRepository tarjetaRepository;

	@Transactional
	@Override
	public List<Movimiento> obtenerMovimientosDeTarjeta(Long idTarjeta) {
		// Obtenermos todos los movimientos con el metodo del repositorio
		return movimientoRepository.obtenerMovimientosDeTarjeta(idTarjeta);
	}

	@Transactional
	@Override
	public List<Movimiento> obtenerMovimientosDeCuenta(Long idCuenta) {
		// Obtenemos todos los movimientos de una cuenta
		return movimientoRepository.obtenerMovimientosDeCuenta(idCuenta);
	}

	@Transactional
	@Override
	public List<Movimiento> obtenerMovimientosFiltrados(MovimientosFilter filtro) {
		Long idMovimiento = filtro.getId();
		if(idMovimiento!=null) {
			Movimiento movimiento=  movimientoRepository.findById(idMovimiento).orElseThrow(()
					-> new EntityNotFoundException("No se ha encontrado movimiento con id: "+idMovimiento));
			List<Movimiento> listaMovimiento = new  ArrayList<Movimiento>();
			listaMovimiento.add(movimiento);
			return listaMovimiento;
		}else {
			
			Date fecha =  filtro.getFecha();
			Double cantidad = filtro.getCantidad();
			TipoMovimiento tipo = filtro.getTipo();
			Long categoriaId = filtro.getCategoriaId();
			Long cuentaId = filtro.getCuentaId();
			Long tarjetaId = filtro.getTarjetaId();
			
			Categoria categoriaBuscada = null;
			Cuenta cuentaBuscada = null;
			Tarjeta tarjetaBuscada = null;
			if(categoriaId != null) {
				try {
					categoriaBuscada = categoriaRepository.findById(categoriaId).orElseThrow(()
							-> new EntityNotFoundException("No se ha encontrado categoria con id: "+categoriaId));
				}catch(EntityNotFoundException e) {
					
				}
			}
			if(cuentaId != null) {
				try {
					cuentaBuscada = cuentaRepository.findById(cuentaId).orElseThrow(()
							-> new EntityNotFoundException("No se ha encontrado cuenta con id: "+cuentaId));
				}catch(EntityNotFoundException e) {
					
				}
			}
			if(tarjetaId != null) {
				try {
					tarjetaBuscada = tarjetaRepository.findById(cuentaId).orElseThrow(()
							-> new EntityNotFoundException("No se ha encontrado tarjeta con id: "+tarjetaId));
				}catch(EntityNotFoundException e) {
					
				}	    	
			}
			
			Movimiento movimientoFiltro = new Movimiento();
			movimientoFiltro.setCantidad(cantidad);
			movimientoFiltro.setCategoria(categoriaBuscada);
			movimientoFiltro.setCuenta(cuentaBuscada);
			movimientoFiltro.setFecha(fecha);
			movimientoFiltro.setTarjeta(tarjetaBuscada);
			movimientoFiltro.setTipo(tipo);
			
			return movimientoRepository.findAll(Example.of(movimientoFiltro));
		}
	}

	@Override
	public List<Movimiento> obtenerMovimientoFechaTarjeta(Long idTarjeta, LocalDate fechaInit, LocalDate fechaFin) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateInit = null;
		Date datefin = null;
		try {
			dateInit = formatter.parse(fechaInit.toString());
			datefin = formatter.parse(fechaFin.toString());
		} catch (ParseException e) {
		} 
		return movimientoRepository.obtenerMovimientosDeTarjetaFechas(idTarjeta, dateInit , datefin);
		
	}
	
	@Override
	public List<Movimiento> obtenerMovimientoFechaCuenta(Long idCuenta, LocalDate fechaInit, LocalDate fechaFin) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateInit = null;
		Date datefin = null;
		try {
			dateInit = formatter.parse(fechaInit.toString());
			datefin = formatter.parse(fechaFin.toString());
		} catch (ParseException e) {
		} 
		
		return movimientoRepository.obtenerMovimientosDeCuentaFechas(idCuenta, dateInit , datefin);
	}

	@Override
	public List<Movimiento> obtenerMovimientosCuentaByCategoriaAndUsuario(Long idCuenta, Long idUsuario, MovimientoMesFilter filtroMovimiento) {
		Date fechaInit = new Date();
		Date fechaFin = new Date();
		try {
			fechaInit= obtenerFechaInicio(filtroMovimiento);
			fechaFin = obtenerFechaFin(filtroMovimiento, fechaInit);
			
		} catch (ParseException e) {
		}
		Long idCategoria = filtroMovimiento.getIdCategoria();
		
		return movimientoRepository.obtenerMovimientosDeCuentaByFechaAndCategoriaAndUsuario(idCuenta,idUsuario,idCategoria ,fechaInit, fechaFin);
	}
	
	@Override
	public List<Movimiento> obtenerMovimientosTarjetaByCategoria(Long idTarjeta, MovimientoMesFilter filtroMovimiento) {
		Date fechaInit = new Date();
		Date fechaFin = new Date();
		try {
			fechaInit= obtenerFechaInicio(filtroMovimiento);
			fechaFin = obtenerFechaFin(filtroMovimiento, fechaInit);
			
		} catch (ParseException e) {
		}
		Long idCategoria = filtroMovimiento.getIdCategoria();
		return movimientoRepository.obtenerMovimientosDeTarjetaByFechaAndCategoria(idTarjeta,idCategoria ,fechaInit, fechaFin);
	}
	

	@Override
	public List<Movimiento> obtenerMovimientosUsuarioByCategoria(Long idUsuario, MovimientoMesFilter filtroMovimiento) {
		Date fechaInit = new Date();
		Date fechaFin = new Date();
		try {
			fechaInit= obtenerFechaInicio(filtroMovimiento);
			fechaFin = obtenerFechaFin(filtroMovimiento, fechaInit);
			
		} catch (ParseException e) {
		}
		Long idCategoria = filtroMovimiento.getIdCategoria();
		return movimientoRepository.obtenerMovimientosDeUsuarioByFechaAndCategoria(idUsuario,idCategoria ,fechaInit, fechaFin);
	}
	
	private Date obtenerFechaInicio(MovimientoMesFilter filtroMovimiento) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int anoActual = LocalDate.now().getYear();
		String mes = filtroMovimiento.getMes();
		return dateFormat.parse(anoActual+"-"+mes+"-01");
	}
	
	private Date obtenerFechaFin(MovimientoMesFilter filtroMovimiento,Date fechaInit) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int anoActual = LocalDate.now().getYear();
		String mes = filtroMovimiento.getMes();
		int ultimoDia = ponerDiasFechaFinMes(fechaInit);
		return dateFormat.parse(anoActual+"-"+mes+"-"+ultimoDia);

		
	}
	
	private static int ponerDiasFechaFinMes(Date fecha){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

 }

	@Transactional
	@Override
	public Movimiento crearMovimiento(Movimiento movimientoNuevo) throws Exception {
		try {
			Optional<Cuenta> cuenta = null;
			Cuenta cuentaObtenida = null;

				// Obtenemos la cuenta de la base de datos que se va a realizar el movimeinto
				cuenta = cuentaRepository.findById(movimientoNuevo.getCuenta().getId());				

			if(cuenta != null) {
				cuentaObtenida = cuenta.get();				
			}
			
			// Calculamos el nuevo saldo actual en el movimiento

			if(TipoMovimiento.INGRESO.equals(movimientoNuevo.getTipo())) {
				movimientoNuevo.setSaldoActual(cuentaObtenida.getSaldo() + movimientoNuevo.getCantidad());				
			}else {
				movimientoNuevo.setSaldoActual(cuentaObtenida.getSaldo() - movimientoNuevo.getCantidad());
			}

			
			// Almacenamos el saldo actual en la cuenta
			cuentaObtenida.setSaldo(movimientoNuevo.getSaldoActual());
			// Guardamos la cuenta actualizada en la base de datos
			Cuenta cuentaGuardada = cuentaRepository.save(cuentaObtenida);
			// Guardamos el movmiento en la base de datos
			movimientoNuevo.setCuenta(cuentaGuardada);
			return movimientoRepository.save(movimientoNuevo);
			
		}catch(Exception e) {
			// En caso de fallar se devuelve un null
			throw new Exception();
		}
	}
	
	@Override
	public Movimiento actualizarMovimiento(Movimiento movimiento) {
		return movimientoRepository.save(movimiento);
	}

	@Override
	public List<Movimiento> obtenerMovimientosDeCuentaOrdenadosFecha(Long idCuenta) {
		return movimientoRepository.obtenerMovimientosDeCuentaOrdenadosCuenta(idCuenta);

	}

	@Override
	public List<Movimiento> obtenerMovimientosDeTarjetaByUsuario(Long idTarjeta, Long idUsuario) {
		return movimientoRepository.obtenerMovimientosDeTarjetaByUsuario(idTarjeta,idUsuario);
	}

	@Override
	public List<Movimiento> obtenerMovimientosDeCuentaByUsuarioOrdenadosFecha(Long idCuenta, Long idUsuario) {
		return movimientoRepository.obtenerMovimientosDeCuentaByUsuarioOrdenadosCuenta(idCuenta,idUsuario);
	}

	@Override
	public List<Movimiento> obtenerMovimientoFechaCuentaByUsuario(Long idCuenta, Long idUsuario, LocalDate fechaInit, LocalDate fechaFin) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateInit = null;
		Date datefin = null;
		try {
			dateInit = formatter.parse(fechaInit.toString());
			datefin = formatter.parse(fechaFin.toString());
		} catch (ParseException e) {
		} 
		
		return movimientoRepository.obtenerMovimientosDeCuentaFechasByUsuario(idCuenta, idUsuario, dateInit , datefin);
	}

	@Override
	public Movimiento save(Movimiento movimientoEditable) {
		return movimientoRepository.save(movimientoEditable);
		
	}

	@Override
	public List<Movimiento> obtenerMovimientosDeCuentaByCuentaAhorro(Long idCuentaAhorro, LocalDate fechaInit, LocalDate fechaFin) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dateInit = null;
		Date datefin = null;
		try {
			dateInit = formatter.parse(fechaInit.toString());
			datefin = formatter.parse(fechaFin.toString());
		} catch (ParseException e) { 		

		}	
		return movimientoRepository.obtenerMovimientosDeCuentaByCuentaAhorro(idCuentaAhorro, dateInit, datefin);

	}

	@Override
	public void eliminar(Movimiento movimiento) {
		movimientoRepository.delete(movimiento);
	}

	@Override
	public Movimiento obtenerMovimientoId(Long id) {
		return movimientoRepository.findById(id).get();
	}


}
