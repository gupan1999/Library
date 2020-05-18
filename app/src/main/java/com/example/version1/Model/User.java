package com.example.version1.Model;

import apijson.MethodAccess;
import apijson.orm.Visitor;

import java.util.List;

@MethodAccess
public class User extends BaseModel implements Visitor<Long> {
    private static final long serialVersionUID = 1L;

    public User() {
        super();
    }
    public User(long id) {
        this();
        setId(id);
    }

    @Override
    public List<Long> getContactIdList() {
        return null;
    }


}

