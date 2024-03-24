package kuloud.cinecritique.photo.entity;

import jakarta.persistence.*;
import kuloud.cinecritique.post.entity.Post;

@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // Constructor, Getters and Setters
}

