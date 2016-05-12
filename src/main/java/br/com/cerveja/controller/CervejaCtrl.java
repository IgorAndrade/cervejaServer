package br.com.cerveja.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.cerveja.exception.ErroImportador;
import br.com.cerveja.exception.RNException;
import br.com.cerveja.model.cerveja.Cerveja;
import br.com.cerveja.model.cerveja.StatusCerveja;
import br.com.cerveja.model.cerveja.Style;
import br.com.cerveja.service.CervejaService;

@RestController
@RequestMapping(value = "/service/cerveja")
public class CervejaCtrl {
	@Autowired
	private CervejaService service;
	@Value("${upload.path}")
	private String uploadPath;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll(
			@RequestParam(value = "pg", defaultValue = "0") Integer pg,
			@RequestParam(value = "qtd", defaultValue = "10") Integer qtd,
			Filter filter,SortParam sort) {

		Pageable pageRequest = null;
		List<Order> orders = new ArrayList<Sort.Order>();
		if (sort.getSortCerveja() != null)
			orders.add(new Order(Direction.fromString(sort.getSortCerveja() ), "name"));
		else if (sort.getSortCervejaria() != null)
			orders.add(new Order(Direction.fromString(sort.getSortCervejaria() ), "name"));
		else
			orders.add(new Order(Direction.ASC , "name"));
			

		pageRequest = new PageRequest(pg, qtd, new Sort(orders));
		Page<Cerveja> lista = service.listar(filter, pageRequest);

		if (lista.getTotalElements() <= 0)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(lista);
	}

	@RequestMapping(value = "/importar/{id}")
	public ResponseEntity importar(@PathVariable("id") String id) {
		try {
			Cerveja cerveja = service.importar(id);
			if (cerveja != null)
				return ResponseEntity.ok(cerveja);
			else
				return ResponseEntity.notFound().build();
		} catch (ErroImportador e) {
			return ResponseEntity.status(417).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/pesquisar")
	public ResponseEntity pesquisar(@QueryParam("q") String q) {
		try {
			if (StringUtils.isBlank(q))
				return ResponseEntity.badRequest().body("Sem query");
			Set lista = service.pesquisar(q);

			if (lista != null)
				return ResponseEntity.ok(lista);
			else
				return ResponseEntity.notFound().build();
		} catch (ErroImportador e) {
			return ResponseEntity.status(417).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(417).body("Erro inesperado");
		}
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public ResponseEntity getById(@PathVariable("id") Long id) {
		try {
			Cerveja cerveja = service.buscarPorId(id);
			if (cerveja != null)
				return ResponseEntity.ok(cerveja);
			else
				return ResponseEntity.notFound().build();

		} catch (Exception e) {
			return ResponseEntity.status(417).body("Erro inesperado");
		}

	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT,
			RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity update(@PathVariable("id") Long id,
			@Valid @RequestBody Cerveja cerveja, Errors erros) {
		try {
			if (!erros.hasErrors()) {
				Cerveja salvo = service.salvar(cerveja);
				return ResponseEntity.ok(salvo);
			} else {
				Map<String, String> listaErros = new HashMap<String, String>();
				for (FieldError ferros : erros.getFieldErrors()) {
					listaErros.put(ferros.getField(),
							ferros.getDefaultMessage());
				}
				return ResponseEntity.badRequest().body(listaErros);
			}

		} catch (Exception e) {
			return ResponseEntity.status(417).body("Erro inesperado");
		}

	}

	@RequestMapping(value = "/upload", method = { RequestMethod.POST })
	public ResponseEntity<?> upload(
			@RequestParam("rotulo") MultipartFile rotulo,
			@RequestParam(value = "garrafa", required = false) MultipartFile garrafa,
			@RequestParam(value = "outros", required = false) MultipartFile[] outros,
			@RequestParam(value = "pasta", required = false) String pasta,
			@RequestParam(value = "id", required = false) Long id) {
		File frotulo = null;
		File fgarrafa = null;
		try {
			if (rotulo != null) {
				frotulo = new File(uploadPath, rotulo.getOriginalFilename());
				rotulo.transferTo(frotulo);
			}
			if (garrafa != null) {
				fgarrafa = new File(uploadPath, rotulo.getOriginalFilename());
				rotulo.transferTo(fgarrafa);
			}
			Cerveja cerveja = service.addImgs(id, frotulo, fgarrafa, null);
			return ResponseEntity.ok(cerveja);
		} catch (RNException rnE) {
			return ResponseEntity.badRequest().body(rnE.getErros());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@RequestMapping(value = "/status", method = { RequestMethod.GET })
	public ResponseEntity<?> getStatus() {

		return ResponseEntity.ok(StatusCerveja.values());

	}
}
