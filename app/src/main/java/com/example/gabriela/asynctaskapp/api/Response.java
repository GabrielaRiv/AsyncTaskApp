package com.example.gabriela.asynctaskapp.api;

import java.util.List;

public class Response {
    private String more;
    private List<ListElements> results;

    public String getMore() {
        return more;
    }

    public void setMore(String more) {
        this.more = more;
    }

    public List<ListElements> getResults() {
        return results;
    }

    public void setResults(List<ListElements> results) {
        this.results = results;
    }


}
