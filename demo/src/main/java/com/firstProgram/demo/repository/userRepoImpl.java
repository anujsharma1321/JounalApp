package com.firstProgram.demo.repository;

import com.firstProgram.demo.entity.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class userRepoImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Users> getUserForSA(){
        Query query=new Query();
        Criteria criteria=new Criteria();
        //query.addCriteria(Criteria.where("email").exists(true));
        //query.addCriteria(Criteria.where("isSentimentAnalysis").is(true));
        query.addCriteria(criteria.andOperator(Criteria.where("email").exists(true), Criteria.where("email").ne(null).ne(""), Criteria.where("isSentimentAnalysis").is(true)));
         //query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"));
        //query.addCriteria(Criteria.where("roles").in("USER","ADMIN"));

        List<Users> ans=mongoTemplate.find(query,Users.class);
        return ans;

    }

}
