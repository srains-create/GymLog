package com.daclink.gymlog.database;

import android.app.Application;
import android.util.Log;

import com.daclink.gymlog.MainActivity;
import com.daclink.gymlog.database.entities.GymLog;
import com.daclink.gymlog.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GymLogRepository {
    private final GymLogDAO gymLogDAO;
    private final UserDAO userDAO;
    private ArrayList<GymLog> allLogs;

    private static GymLogRepository repository;

    private GymLogRepository(Application application){
        GymLogDatabase db = GymLogDatabase.getDatabase(application);
        this.gymLogDAO = db.gymLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<GymLog>) this.gymLogDAO.getAllRecords();
    }

    public static GymLogRepository getRepository(Application application){
                if (repository != null){
                    return repository;
                }
                Future<GymLogRepository> future = GymLogDatabase.databaseWriteExecutor.submit(
                    new Callable<GymLogRepository>() {
                       @Override
                       public GymLogRepository call() throws Exception {
                        return new GymLogRepository(application);
                       }
                }
        );
        try{
            return future.get();

        }catch(InterruptedException | ExecutionException e){
            Log.d(MainActivity.TAG,"Problem getting GymRepository, thread error.");
        }
        return null;
    }



    public ArrayList<GymLog> getAllLogs() {
        // Using Future to get the result from a background thread
        Future<ArrayList<GymLog>> future = GymLogDatabase.databaseWriteExecutor.submit(
                // Callable to fetch all records
                new Callable<ArrayList<GymLog>>() {
                    // Override the call method to return all GymLog records
                    @Override
                    // Fetch and return all GymLog records from the DAO
                    public ArrayList<GymLog> call() throws Exception {
                        // Fetch and return all GymLog records from the DAO
                        return (ArrayList<GymLog>) gymLogDAO.getAllRecords();
                    }
                }
        );
        // Try to get the result from the Future
        try{
            // Return the fetched GymLog records
            return future.get();
            // Catch any InterruptedException or ExecutionException that may occur
        } catch (InterruptedException |ExecutionException e){
            // Log an error message if there is a problem getting the records
            Log.i(MainActivity.TAG,"Problem when getting all GymLogs in the repository.");
        }
        // Return null if there was an error
        return null;

    }
    public void insertGymLog(GymLog gymLog){
        GymLogDatabase.databaseWriteExecutor.execute(()->{
            gymLogDAO.insert(gymLog);
        });
    }

    public void insertUser(User... user){
        GymLogDatabase.databaseWriteExecutor.execute(()->{
            userDAO.insert(user);
        });
    }
}
