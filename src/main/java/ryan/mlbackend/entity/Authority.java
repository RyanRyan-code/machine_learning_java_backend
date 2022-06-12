package ryan.mlbackend.entity;



import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String authority;






}