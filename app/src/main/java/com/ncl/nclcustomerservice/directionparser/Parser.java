package com.ncl.nclcustomerservice.directionparser;


import java.util.List;

//. by Haseem Saheed
public interface Parser {
    List<Route> parse() throws RouteException;
}