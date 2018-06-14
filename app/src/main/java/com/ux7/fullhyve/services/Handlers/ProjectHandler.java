package com.ux7.fullhyve.services.Handlers;

import android.app.Activity;

import com.ux7.fullhyve.services.Models.MyProject;
import com.ux7.fullhyve.services.Models.Task;
import com.ux7.fullhyve.services.Utility.Converter;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.github.nkzawa.socketio.client.Ack;
import com.ux7.fullhyve.ui.data.ListMember;
import com.ux7.fullhyve.ui.data.ListTask;
import com.ux7.fullhyve.ui.data.ListTaskSet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ProjectHandler extends Handler {
    public void getMyProjects(final int offset, final int limit, final Activity activity, final Runnable runnable){
        final List<MyProject> myProjects = new ArrayList<>();
        //myProjects=cache.contacts.getMyTeams(offset,limit).toArray();

        if(myProjects.size()>0){
            activity.runOnUiThread(runnable);
        }else {
            HashMap<String, Object> args = new HashMap<>();
            args.put("offset", offset);
            args.put("limit", limit);

            JSONObject req = RequestFormat.createRequestObj("getMyProjects", args);

            socket.emit("getMyProjects", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if (generalHandler(args) == 200) {
                        final ResponseFormat.GetMyProjectsR myProjectsR = gson.fromJson(args[0].toString(), ResponseFormat.GetMyProjectsR.class);

                        if (myProjectsR != null) {
                            //cache.contacts.addReceivedMessage(friendId, {message});
                            //cache.teams.myTeams.addTeams(teamsR.data);
                        }

                        activity.runOnUiThread(runnable);
                    }
                }

            });
        }
    }




    public void editProjectDetails(String name, String image, String field, String description, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("image", image);
        args.put("field", field);
        args.put("description", description);

        JSONObject req = RequestFormat.createRequestObj("editProjectDetails",args);

        socket.emit("editProjectDetails", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.StatusR statusR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void searchProjects(final int offset, final int limit,String name, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("offset",offset);
        args.put("limit", limit);
        args.put("name",name);

        JSONObject req = RequestFormat.createRequestObj("searchProjects",args);

        socket.emit("searchProjects", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.SearchProjectsR searchProjectsR = gson.fromJson(args[0].toString(), ResponseFormat.SearchProjectsR.class);

                    if(searchProjectsR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }



    public void getContributors(int projectId, final int offset, final int limit, final List<ListMember> listMembers, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("projectId",projectId);
        args.put("offset",offset);
        args.put("limit", limit);

        JSONObject req = RequestFormat.createRequestObj("getContributors",args);

        socket.emit("getContributors", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetProjectContributorsR projectContributorsR = gson.fromJson(args[0].toString(), ResponseFormat.GetProjectContributorsR.class);

                    if(projectContributorsR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    listMembers.clear();
                    listMembers.addAll(Converter.portUsersToListMember(projectContributorsR.data.individuals));

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void getTaskSets(int projectId, final int offset, final int limit, final List<ListTaskSet> listTaskSets, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("projectId",projectId);
        args.put("offset",offset);
        args.put("limit", limit);

        JSONObject req = RequestFormat.createRequestObj("getTaskSets",args);

        socket.emit("getTaskSets", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetTaskSetsR taskSetsR = gson.fromJson(args[0].toString(), ResponseFormat.GetTaskSetsR.class);

                    if(taskSetsR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    listTaskSets.clear();
                    listTaskSets.addAll(Converter.portTaskSetToListTaskSet(taskSetsR.data.taskSets));

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }




    public void getTasks(int projectId, int tasksetId, final int offset, final int limit, final List<ListTask> listTasks, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("projectId",projectId);
        args.put("tasksetId",tasksetId);
        args.put("offset",offset);
        args.put("limit", limit);

        JSONObject req = RequestFormat.createRequestObj("getTasks",args);

        socket.emit("getTasks", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetTasksR tasksR = gson.fromJson(args[0].toString(), ResponseFormat.GetTasksR.class);

                    if(tasksR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    listTasks.clear();
                    listTasks.addAll(Converter.portTaskToListTask(tasksR.data.tasks));

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }






    public void newProject(String name,String image,String field, String description, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("name",name);
        args.put("image",image);
        args.put("field",field);
        args.put("description", description);

        JSONObject req = RequestFormat.createRequestObj("newProject",args);

        socket.emit("newProject", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.CreateNewProject newProjectR = gson.fromJson(args[0].toString(), ResponseFormat.CreateNewProject.class);

                    if(newProjectR!=null){
                        //cache.contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }



    public void addContributors(int[] teamIds, int[] individualIds, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamIds",teamIds);
        args.put("individualIds",individualIds);

        JSONObject req = RequestFormat.createRequestObj("addContributors",args);

        socket.emit("addContributors", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }




    public void removeContributors(int[] teamIds, int[] individualIds, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("teamIds",teamIds);
        args.put("individualIds",individualIds);

        JSONObject req = RequestFormat.createRequestObj("removeContributors",args);

        socket.emit("removeContributors", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }





    public void replyIndividualContributorJoinRequest(int requestId, Boolean accepted, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("requestId",requestId);
        args.put("accepted",accepted);

        JSONObject req = RequestFormat.createRequestObj("replyIndividualContributorJoinRequest",args);

        socket.emit("replyIndividualContributorJoinRequest", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void replyTeamContributorJoinRequest(int requestId,Boolean accepted, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("requestId",requestId);
        args.put("accepted",accepted);

        JSONObject req = RequestFormat.createRequestObj("replyTeamContributorJoinRequest",args);

        socket.emit("replyTeamContributorJoinRequest", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }





    public void getMyProjectDetails(int projectId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("projectId",projectId);

        JSONObject req = RequestFormat.createRequestObj("getMyProjectDetails",args);

        socket.emit("getMyProjectDetails", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void deleteProject(final int projectId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("projectId",projectId);

        JSONObject req = RequestFormat.createRequestObj("deleteProject",args);

        socket.emit("deleteProject", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void newTaskSet(String name, Date deadline, String description, int projectId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("name",name);
        args.put("projectId",projectId);

        JSONObject req = RequestFormat.createRequestObj("newTaskset", args);

        socket.emit("newTaskset", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.CreateNewTasksetR newTaskR = gson.fromJson(args[0].toString(), ResponseFormat.CreateNewTasksetR.class);

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }



    public void deleteTaskset(int tasksetId,int projectId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("tasksetId",tasksetId);
        args.put("projectId",projectId);

        JSONObject req = RequestFormat.createRequestObj("deleteTaskset",args);

        socket.emit("deleteTaskset", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.StatusR statusR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }










    public void newTask(Task taskData, int taskSetId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("taskSetId",taskSetId);
        args.put("taskData",taskData);

        JSONObject req = RequestFormat.createRequestObj("newTask",args);

        socket.emit("newTask", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.CreateNewTaskR newTaskR = gson.fromJson(args[0].toString(), ResponseFormat.CreateNewTaskR.class);

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void startTask(int taskId,int projectId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("taskId",taskId);
        args.put("projectId",projectId);

        JSONObject req = RequestFormat.createRequestObj("startTask",args);

        socket.emit("startTask", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.StatusR statusR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }



    public void completeTask(int taskId,int projectId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("taskId",taskId);
        args.put("projectId",projectId);

        JSONObject req = RequestFormat.createRequestObj("completeTask",args);

        socket.emit("completeTask", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.StatusR statusR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }

    public void changeTaskStatus(int taskId,int projectId,String taskStatus, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("taskId",taskId);
        args.put("projectId",projectId);
        args.put("taskStatus",taskStatus);

        JSONObject req = RequestFormat.createRequestObj("changeTaskStatus",args);

        socket.emit("changeTaskStatus", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.StatusR statusR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }






    public void deleteTask(int taskId,int projectId, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("taskId",taskId);
        args.put("projectId",projectId);

        JSONObject req = RequestFormat.createRequestObj("deleteTask",args);

        socket.emit("deleteTask", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.StatusR statusR = gson.fromJson(args[0].toString(), ResponseFormat.StatusR.class);

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }
}
