package com.littleneighbors.utils;

import com.littleneighbors.features.family.dto.FamilyResponse;
import com.littleneighbors.features.family.model.Family;
import com.littleneighbors.features.user.dto.UserResponse;
import com.littleneighbors.features.user.model.Role;
import com.littleneighbors.features.user.model.User;
//import com.littleneighbors.features.child.dto.ChildResponse;
//import com.littleneighbors.features.child.model.Child;
//import com.littleneighbors.features.match.dto.MatchResponse;
//import com.littleneighbors.features.match.model.Match;
//import com.littleneighbors.features.interest.dto.InterestResponse;
//import com.littleneighbors.features.interest.model.Interest;
import com.littleneighbors.features.neighborhood.model.Neighborhood;

import java.time.LocalDateTime;

public class SuperTestDataFactory {

    // ---------------- FAMILY ----------------
    public static Family family(Long id) {
        Neighborhood neighborhood = Neighborhood.builder()
                .id(1L)
                .name("Mislata")
                .streetName("Archena")
                .build();

        return Family.builder()
                .id(id)
                .representativeName("Alexandra Rojas")
                .area("Valencia")
                .neighborhood(neighborhood)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static FamilyResponse familyResponse(Long id) {
        Family f = family(id);
        FamilyResponse response = new FamilyResponse();
        response.setId(f.getId());
        response.setRepresentativeName(f.getRepresentativeName());
        response.setArea(f.getArea());
        response.setNeighborhood(f.getNeighborhood().getName());
        return response;
    }

    // ---------------- USER ----------------
    public static User user(Long id) {
        User u = new User();
        u.setId(id);
        u.setEmail("user" + id + "@test.com");
        u.setPassword("password123!");
        u.setRole(Role.ROLE_USER);
        return u;
    }

    public static UserResponse userResponse(Long id) {
        User u = user(id);
        UserResponse r = new UserResponse();
        r.setId(u.getId());
        r.setEmail(u.getEmail());
        r.setRole(u.getRole().name());
        return r;
    }

    // ---------------- CHILD ----------------
//    public static Child child(Long id) {
//        Child c = new Child();
//        c.setId(id);
//        c.setName("Niño Test " + id);
//        c.setAge(7);
//        return c;
//
//        public static ChildResponse childResponse (Long id){
//            Child c = child(id);
//            ChildResponse r = new ChildResponse();
//            r.setId(c.getId());
//            r.setName(c.getName());
//            r.setAge(c.getAge());
//            return r;
//        }
//
//        // ---------------- MATCH ----------------
//        public static Match match (Long id){
//            Match m = new Match();
//            m.setId(id);
//            m.setDescription("Match test " + id);
//            return m;
//        }
//
//        public static MatchResponse matchResponse (Long id){
//            Match m = match(id);
//            MatchResponse r = new MatchResponse();
//            r.setId(m.getId());
//            r.setDescription(m.getDescription());
//            return r;
//        }
//
//        // ---------------- INTEREST ----------------
//        public static Interest interest (Long id){
//            Interest i = new Interest();
//            i.setId(id);
//            i.setName("Interest Test " + id);
//            return i;
//        }
//
//        public static InterestResponse interestResponse (Long id){
//            Interest i = interest(id);
//            InterestResponse r = new InterestResponse();
//            r.setId(i.getId());
//            r.setName(i.getName());
//            return r;
//        }
//    }
}