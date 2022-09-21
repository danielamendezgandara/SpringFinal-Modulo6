package cl.awakelab.empresaprevencionriesgos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class EmpresaprevencionriesgosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpresaprevencionriesgosApplication.class, args);
		log.info("Iniciando la aplicación....");

		/*
		 * Ejemplo de encriptación de contraseñas. La contraseña ingresada por el
		 * usuario se almacenará en la base de datos encriptada Descomente el código
		 * comentado para ver la encriptación de contraseñas ejemplo
		 */

		// Código ejemplo encriptación contraseña

		/*
		 * String[] originalPassword = {"1234", "jeremy123", "jing123"}; PasswordEncoder
		 * encoder = new BCryptPasswordEncoder(); String hashedPassword = "";
		 * System.out.println("ORIGINAL \t HASHED");
		 * System.out.println("=========\t======="); for(String password :
		 * originalPassword){ hashedPassword = encoder.encode(password);
		 * System.out.println(password + "\t\t" + hashedPassword); }
		 */
	}
}
