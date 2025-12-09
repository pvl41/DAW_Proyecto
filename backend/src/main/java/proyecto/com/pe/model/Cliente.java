package proyecto.com.pe.model;
import jakarta.persistence.*; // Usando jakarta.persistence.* para modernidad
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert; // Opcional, dependiendo de la configuración

@Entity
@Table(name = "cliente")
@DynamicInsert
@Getter
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCliente")
    private Integer idCliente;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "apellido", length = 100, nullable = false)
    private String apellido;

    @Column(name = "dni", length = 10, nullable = false, unique = true)
    private String dni;

    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "activo", columnDefinition = "TINYINT(1)")
    private Boolean activo = true;



}