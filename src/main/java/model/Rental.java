package model;

import exception.DVDAlreadyExistsException;
import exception.UserAlreadyExistsException;
import io.files.FileManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.*;


public class Rental {

    private Map<Integer, DVD> dvds = new HashMap<>();
    private Map<Integer, RentalUser> users = new HashMap<>();
    public Map<Integer, DVD> getDVDs() {
        return dvds;
    }
    private Map<Integer, RentalDetails> rentalDetails = new HashMap<>();
    public Map<Integer, RentalDetails> getRentedDetails() {
        return rentalDetails;
    }



    public Collection<DVD> getSortedDVDs(Comparator<DVD> comparator) {
        ArrayList<DVD> list = new ArrayList<>(this.dvds.values());


        list.sort(comparator);
        return list;
    }
    public Collection<DVD> getSortedAvaiableDVDs(Comparator<DVD> comparator) {
        ArrayList<DVD> list = new ArrayList<>(this.dvds.values());
        ArrayList<DVD> avaiableList = new ArrayList<>();
            for(int i=0;i< list.size();i++){
                if(list.get(i).getIsAvailable()==true){
                   avaiableList.add(list.get(i));
                }
            }
        avaiableList.sort(comparator);
        return avaiableList;
    }
    public Collection<DVD> getSortedRentedDVDs(Comparator<DVD> comparator,int userId) {
        ArrayList<DVD> list = new ArrayList<>(this.dvds.values());
        ArrayList<DVD> avaiableList = new ArrayList<>();
        for(int i=0;i< list.size();i++){
            if(list.get(i).getUserId()==userId){
                avaiableList.add(list.get(i));
            }
        }
        avaiableList.sort(comparator);
        return avaiableList;
    }


    public Collection<RentalDetails> getSortedRentedDetails(Comparator<RentalDetails> comparator) {
        ArrayList<RentalDetails> list = new ArrayList<>(this.rentalDetails.values());
        list.sort(comparator);
        return list;
    }


    public Map<Integer, RentalUser> getUsers() {
        return users;
    }
    public Collection<RentalUser> getSortedUsers(Comparator<RentalUser> comparator) {
        ArrayList<RentalUser> list = new ArrayList<>(this.users.values());
        list.sort(comparator);
        return list;
    }
    public void addUser(RentalUser user) {
        int userId=users.size()+1;
        user.setUserId(userId);
        users.put(userId, user);
    }
    public void addRent(RentalDetails details) {
        rentalDetails.put(details.getRentalId(),details);
    }

    public void addDVD(DVD dvd) {
        int dvdId=dvds.size()+1;
        dvd.setDvdId(dvdId);
        dvds.put(dvd.getDvdId(), dvd);
    }
    public Optional<DVD> findDVDBydvdId(Integer dvdId) {
        return Optional.ofNullable(dvds.get(dvdId));
    }
    public Optional<RentalUser> findUserByUserId(int userId) {
        return Optional.ofNullable(users.get(userId));
    }
    public boolean removeDVD (DVD dvd) {
        if(dvds.containsValue(dvd)) {
            dvds.remove(dvd.getTitle());
            return true;
        } else {
            return false;
        }
    }
    public void rentDVD(RentalDetails details) {
        Optional<DVD> dvdOp = findDVDBydvdId(details.getDvdId());
        DVD dvd = dvdOp.get();
        RentalUser user = findUserByUserId(details.getUserId()).get();
        if(dvd.getIsAvailable()!=true) throw new UserAlreadyExistsException(
                "DVD is not available " + dvd.getDvdId()
        );
            user.addToRentedItems(details);
            dvd.setIsAvailable(false);
            dvd.setUserId(user.getUserId());
            int rentalId = Integer.valueOf(rentalDetails.size()+1);
            details.setRentalId(rentalId);
            rentalDetails.put(rentalId, details);
        }



}
