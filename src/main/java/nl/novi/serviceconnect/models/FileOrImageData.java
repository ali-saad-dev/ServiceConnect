package nl.novi.serviceconnect.models;

import jakarta.persistence.*;

@Entity
@Table(name = "serviceFileOrImageData")
public class FileOrImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @Lob
    private byte[] fileOrImageData;

    @OneToOne
    @JoinColumn(name= "service-id", referencedColumnName = "id")
    private Service service;

    public FileOrImageData() {}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public byte[] getFileOrImageData() { return fileOrImageData; }

    public void setFileOrImageData(byte[] fileOrImageData) { this.fileOrImageData = fileOrImageData; }

    public Service getService() { return service; }

    public void setService(Service service) { this.service = service; }
}
