package com.tourism.guide.config;
import com.tourism.guide.model.Site;
import com.tourism.guide.repository.SiteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
@Configuration
public class DataSeeder {
 @Bean CommandLineRunner seed(SiteRepository repo){return args->{
  if(repo.count()>0)return;
  repo.save(new Site("Gandikota Fort","Andhra Pradesh","Fort",14.8146,78.2884,"The dramatic Pennar canyon fort known as the Grand Canyon of India.","Gandikota is a historic fort complex on the Pennar river, famous for its gorge, fort walls and temples.","Rayalaseema cuisine","Pennar viewpoint at sunrise","https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"));
  repo.save(new Site("Warangal Fort","Telangana","Heritage",17.9689,79.5941,"A major Kakatiya-era heritage site with monumental gateways.","Warangal Fort reflects the architecture and political history of the Kakatiya dynasty.","Sarva Pindi","Thousand Pillar Temple area",""));
  repo.save(new Site("Charminar","Telangana","Monument",17.3616,78.4747,"Hyderabad’s iconic four-minaret monument in the Old City.","Charminar was built in 1591 and remains a symbol of Hyderabad.","Hyderabadi Biryani","Laad Bazaar lanes",""));
 };}
}
