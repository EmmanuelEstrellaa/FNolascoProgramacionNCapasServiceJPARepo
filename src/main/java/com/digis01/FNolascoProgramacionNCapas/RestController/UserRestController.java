package com.digis01.FNolascoProgramacionNCapas.RestController;

import com.digis01.FNolascoProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.digis01.FNolascoProgramacionNCapas.DataAccesObject.UsuarioDAO;
import com.digis01.FNolascoProgramacionNCapas.JPA.Colonia;
import com.digis01.FNolascoProgramacionNCapas.JPA.Direccion;
import com.digis01.FNolascoProgramacionNCapas.JPA.Result;
import com.digis01.FNolascoProgramacionNCapas.JPA.ResultFile;
import com.digis01.FNolascoProgramacionNCapas.JPA.Roll;
import com.digis01.FNolascoProgramacionNCapas.JPA.Usuario;
import com.digis01.FNolascoProgramacionNCapas.JPA.UsuarioDireccion;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/usuarioapi")
public class UserRestController {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private UsuarioDAO UsuarioDAO;

    @GetMapping
    public ResponseEntity GetAll() {
        Result result = usuarioDAOImplementation.GetAllJPA();

        if (result.correct) {
            if (result.objects.isEmpty()) {
                return ResponseEntity.status(204).body(null);
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/Add")
    public ResponseEntity Add(@RequestBody UsuarioDireccion usuarioDireccion) {

        Result result = usuarioDAOImplementation.AddJPA(usuarioDireccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/AddDireccion")
    public ResponseEntity AddDireccion(@RequestBody UsuarioDireccion usuarioDireccion) {
        Result result = usuarioDAOImplementation.AddDireccionJPA(usuarioDireccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @PostMapping("/update")
    public ResponseEntity UpdateUsuario(@RequestBody Usuario usuario) {

        Result result = usuarioDAOImplementation.UsuarioUpdateJPA(usuario);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/delete/{IdUsuario}")
    public ResponseEntity UsuarioDelete(@PathVariable int IdUsuario) {
        Result result = usuarioDAOImplementation.DeleteUsuarioDireccionJPA(IdUsuario);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/deleteDir/{IdDireccion}")
    public ResponseEntity DireccionDelete(@PathVariable int IdDireccion) {
        Result result = usuarioDAOImplementation.DireccionDeleteJPA(IdDireccion);

        if (result.correct) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/CargaMasiva")
    public ResponseEntity CargaMasiva(@RequestParam("archivo") MultipartFile archivo) {
        Result result = new Result();

        //Guardarlo en un punto del sistema
        if (archivo != null && !archivo.isEmpty()) { //El archivo no sea nulo ni este vacio
            try {
                String tipoArchivo = archivo.getOriginalFilename().split("\\.")[1];

                String root = System.getProperty("user.dir");
                String path = "src/main/resources/static/archivos";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));
                String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();
                archivo.transferTo(new File(absolutePath));

                //Leer el archivo
                List<UsuarioDireccion> listaUsuarios = new ArrayList();
                if (tipoArchivo.equals("txt")) {
                    listaUsuarios = LecturaArchivoTXT(new File(absolutePath)); //método para leer la lista
                } else {
                    listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
                }

                //Validar el archivo
                List<ResultFile> listaErrores = new ArrayList<>();

                if (listaErrores.isEmpty()) {
                    //Proceso el archivo
                    result.correct = true;
                    result.object = absolutePath;
                    return ResponseEntity.ok(result);
                } else {
                    result.correct = false;
                    result.objects = new ArrayList<>();

                    for (ResultFile error : listaErrores) {
                        result.objects.add(error);
                    }
                    return ResponseEntity.status(400).body(result);
                }

            } catch (Exception ex) {
                return ResponseEntity.status(500).body("Todo mal");
            }
        } else {
            result.correct = false;
            return ResponseEntity.status(400).body(result);
        }

    }

    @PostMapping("/CargaMasiva/Procesar")
    public ResponseEntity Procesar(@RequestBody String absolutePath) {
        Result result = new Result();

        try {
            String tipoArchivo = absolutePath.split("\\.")[1];

            List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
            if (tipoArchivo.equals("txt")) {
                listaUsuarios = LecturaArchivoTXT(new File(absolutePath));

            } else {
                listaUsuarios = LecturaArchivoExcel(new File(absolutePath));
            }

            for (UsuarioDireccion usuario : listaUsuarios) {
                usuarioDAOImplementation.AddJPA(usuario);
            }

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
        }

        return ResponseEntity.ok(result);
    }

    public List<UsuarioDireccion> LecturaArchivoTXT(File archivo) {
        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();

        try (FileReader fileReader = new FileReader(archivo); BufferedReader bufferedReader = new BufferedReader(fileReader);) {

            String linea;

            while ((linea = bufferedReader.readLine()) != null) {
                String[] campos = linea.split("\\|");

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.Usuario = new Usuario();
                usuarioDireccion.Usuario.setNombre(campos[0]);
                usuarioDireccion.Usuario.setApellidoPaterno(campos[1]);
                usuarioDireccion.Usuario.setApellidoMaterno(campos[2]);
                usuarioDireccion.Usuario.setUserName(campos[3]);
                usuarioDireccion.Usuario.setEmail(campos[4]);
                usuarioDireccion.Usuario.setSexo(campos[5]);
                usuarioDireccion.Usuario.setTelefono(campos[6]);
                usuarioDireccion.Usuario.setCelular(campos[7]);
                usuarioDireccion.Usuario.setCurp(campos[8]);
                usuarioDireccion.Usuario.setPassword(campos[9]);
                //Darle formato a la fecha de nacimiento
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); //Dar formato a la fecha
                usuarioDireccion.Usuario.setFechaNacimiento(formatter.parse(campos[10]));
//                usuarioDireccion.Usuario.setStatus(Integer.parseInt(campos[6]));
                usuarioDireccion.Usuario.setImagen(null);
                usuarioDireccion.Usuario.Roll = new Roll();
                usuarioDireccion.Usuario.Roll.setIdRoll(Integer.parseInt(campos[11]));

                usuarioDireccion.Direccion = new Direccion();
                usuarioDireccion.Direccion.setCalle(campos[12]);
                usuarioDireccion.Direccion.setNumeroExterior(campos[13]);
                usuarioDireccion.Direccion.setNumeroInterior(campos[14]);

                usuarioDireccion.Direccion.Colonia = new Colonia();
                usuarioDireccion.Direccion.Colonia.setIdColonia(Integer.parseInt(campos[15]));

                listaUsuarios.add(usuarioDireccion);
            }

        } catch (Exception ex) {
            listaUsuarios = null;

        }

        return listaUsuarios;
    }

    public List<UsuarioDireccion> LecturaArchivoExcel(File archivo) {
        List<UsuarioDireccion> listaUsuarios = new ArrayList<>();
        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            for (Sheet sheet : workbook) {

                for (Row row : sheet) {

                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setNombre(row.getCell(0).toString());
                    usuarioDireccion.Usuario.setApellidoPaterno(row.getCell(1).toString());
                    usuarioDireccion.Usuario.setApellidoMaterno(row.getCell(2).toString());
                    usuarioDireccion.Usuario.setUserName(row.getCell(3).toString());
                    usuarioDireccion.Usuario.setEmail(row.getCell(4).toString());
                    usuarioDireccion.Usuario.setPassword(row.getCell(5).toString());
                    usuarioDireccion.Usuario.setSexo(row.getCell(6).toString());
                    usuarioDireccion.Usuario.setTelefono(row.getCell(7).toString());
                    usuarioDireccion.Usuario.setCelular(row.getCell(8).toString());
                    usuarioDireccion.Usuario.setCurp(row.getCell(9).toString());
                    Cell cell = row.getCell(10);
                    Date fechaNacimiento;
                    if (cell.getCellType() == CellType.NUMERIC) {
                        fechaNacimiento = cell.getDateCellValue();
                    } else {
                        String fechaS = cell.toString();
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                        fechaNacimiento = formato.parse(fechaS);
                    }
                    usuarioDireccion.Usuario.setFechaNacimiento(fechaNacimiento);
                    usuarioDireccion.Usuario.Roll = new Roll();
                    usuarioDireccion.Usuario.Roll.setIdRoll((int) row.getCell(11).getNumericCellValue());
//                    usuarioDireccion.Usuario.setStatus(row.getCell(3) != null ? (int) row.getCell(3).getNumericCellValue() : 0 );
                    usuarioDireccion.Direccion = new Direccion();
                    usuarioDireccion.Direccion.setCalle(row.getCell(12).toString());
                    usuarioDireccion.Direccion.setNumeroExterior(row.getCell(13).toString());
                    usuarioDireccion.Direccion.setNumeroInterior(row.getCell(14).toString());

                    usuarioDireccion.Direccion.Colonia = new Colonia();
                    usuarioDireccion.Direccion.Colonia.setIdColonia((int) row.getCell(15).getNumericCellValue());
                    listaUsuarios.add(usuarioDireccion);
                }

            }
        } catch (Exception ex) {
            System.out.println("Error al abrir el archivo");
        }

        return listaUsuarios;
    }

    public List<ResultFile> ValidarArchivo(List<UsuarioDireccion> listaUsuarios) {
        List<ResultFile> listaErrores = new ArrayList<>();

        if (listaUsuarios == null) {
            listaErrores.add(new ResultFile(0, "La lista es nula", "La lista es nula"));
        } else if (listaUsuarios.isEmpty()) {
            listaErrores.add(new ResultFile(0, "La lista está vacía", "La lista está vacía"));
        } else {
            int fila = 1;
            for (UsuarioDireccion usuarioDireccion : listaUsuarios) {
                if (usuarioDireccion.Usuario.getNombre() == null || usuarioDireccion.Usuario.getNombre().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getNombre(), "El nombre es un campo oligatorio"));
                }

                if (usuarioDireccion.Usuario.getApellidoPaterno() == null || usuarioDireccion.Usuario.getApellidoPaterno().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getApellidoPaterno(), "El Apellido Paterno es un campo oligatorio"));
                }

                if (usuarioDireccion.Usuario.getUserName() == null || usuarioDireccion.Usuario.getUserName().equals("")) {
                    listaErrores.add(new ResultFile(fila, usuarioDireccion.Usuario.getApellidoPaterno(), "El Username es un campo oligatorio"));
                }
                fila++;
            }
        }
        return listaErrores;
    }

    @PostMapping("/busquedaDinamica")
    public ResponseEntity GetAllDinamico(@RequestBody Usuario usuario) {
        Result result = usuarioDAOImplementation.GetAllDinamico(usuario);

        if (result.correct) {
            if (result.correct = false) {
                return ResponseEntity.status(204).body(null);
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @GetMapping("/getbyid/{IdUsuario}")
    public ResponseEntity<Usuario> UserGetByID(@PathVariable int IdUsuario) {
        return UsuarioDAO.findById(IdUsuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
