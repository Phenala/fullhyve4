package com.ux7.fullhyve.services.Handlers;

import android.app.Activity;
import android.util.Log;

import com.ux7.fullhyve.services.Models.MyProject;
import com.ux7.fullhyve.services.Models.Project;
import com.ux7.fullhyve.services.Models.Task;
import com.ux7.fullhyve.services.Storage.ProjectCacheManager;
import com.ux7.fullhyve.services.Utility.Converter;
import com.ux7.fullhyve.services.Utility.Realtime;
import com.ux7.fullhyve.services.Utility.RequestFormat;
import com.ux7.fullhyve.services.Utility.ResponseFormat;
import com.github.nkzawa.socketio.client.Ack;
import com.ux7.fullhyve.ui.data.ListMember;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ListTask;
import com.ux7.fullhyve.ui.data.ListTaskSet;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class ProjectHandler extends Handler {
    public void getMyProjects(final int offset, final int limit, final List<ListProject> listProjects, final Activity activity, final Runnable runnable){
        List<MyProject> myProjects;

        myProjects = ProjectCacheManager.getMyProjects(offset, limit);

        if(myProjects!=null && myProjects.size() > 0){
            listProjects.clear();
            listProjects.addAll(Converter.portMyProjectToListProject(myProjects));

            activity.runOnUiThread(runnable);
        }else {
            HashMap<String, Object> args = new HashMap<>();
            args.put("offset", offset);
            args.put("limit", limit);

            JSONObject req = RequestFormat.createRequestObj("getMyProjects", args);

            Realtime.socket.emit("getMyProjects", req, new Ack() {
                @Override
                public void call(Object... args) {
                    if (generalHandler(args) == 200) {
                        final ResponseFormat.GetMyProjectsR myProjectsR = gson.fromJson(args[0].toString(), ResponseFormat.GetMyProjectsR.class);

                        if (myProjectsR != null && myProjectsR.data.myProjects != null) {
                            //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
                            //AppData.getCache().teams.myTeams.addTeams(teamsR.data);
                        }

                        listProjects.clear();
                        listProjects.addAll(Converter.portMyProjectToListProject(myProjectsR.data.myProjects));

                        activity.runOnUiThread(runnable);
                    }
                }

            });
        }
    }




    public void editProjectDetails(final String name, final String image, final String field, final String description, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("image", image);
        args.put("field", field);
        args.put("description", description);

        JSONObject req = RequestFormat.createRequestObj("editProjectDetails",args);

        Realtime.socket.emit("editProjectDetails", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    ProjectCacheManager.editProjectDetails(new Project(0, name, image, description, field, 0));
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }


    public void searchProjects(final int offset, final int limit,String name, final List<ListProject> listProjects, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("offset",offset);
        args.put("limit", limit);
        args.put("name",name);

        JSONObject req = RequestFormat.createRequestObj("searchProjects",args);

        Realtime.socket.emit("searchProjects", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.SearchProjectsR searchProjectsR = gson.fromJson(args[0].toString(), ResponseFormat.SearchProjectsR.class);

                    if(searchProjectsR!=null){
                        //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    listProjects.clear();
                    listProjects.addAll(Converter.portMyProjectToListProject(searchProjectsR.data.myProjects));
                    listProjects.addAll(Converter.portProjectToListProject(searchProjectsR.data.projects));

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

        Realtime.socket.emit("getContributors", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetProjectContributorsR projectContributorsR = gson.fromJson(args[0].toString(), ResponseFormat.GetProjectContributorsR.class);

                    if(projectContributorsR!=null){
                        //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    listMembers.clear();
                    listMembers.addAll(Converter.portUsersToListMember(projectContributorsR.data.individuals));
                    listMembers.addAll(Converter.portTeamToListMember(projectContributorsR.data.teams));

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

        JSONObject req = RequestFormat.createRequestObj("getTasksets",args);

        Realtime.socket.emit("getTasksets", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetTaskSetsR taskSetsR = gson.fromJson(args[0].toString(), ResponseFormat.GetTaskSetsR.class);

                    if(taskSetsR!=null && taskSetsR.data.tasksets!=null){
                        Log.e("",taskSetsR.data.tasksets.size()+"");
                        //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    listTaskSets.clear();
                    listTaskSets.addAll(Converter.portTaskSetToListTaskSet(taskSetsR.data.tasksets));

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

        Realtime.socket.emit("getTasks", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.GetTasksR tasksR = gson.fromJson(args[0].toString(), ResponseFormat.GetTasksR.class);

                    if(tasksR!=null){
                        //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
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

        Realtime.socket.emit("newProject", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    final ResponseFormat.CreateNewProject newProjectR = gson.fromJson(args[0].toString(), ResponseFormat.CreateNewProject.class);

                    if(newProjectR!=null){
                        //AppData.getCache().contacts.addReceivedMessage(friendId, {message});
                        //AppData.userToken = teamsR.data.message;
                    }

                    activity.runOnUiThread(runnable);
                }
            }
        });
    }



    public void addContributors(int projectId, int[] teamIds, int[] individualIds, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("projectId",projectId);
        args.put("teamIds",teamIds);
        args.put("individualIds",individualIds);

        JSONObject req = RequestFormat.createRequestObj("addContributors",args);

        Realtime.socket.emit("addContributors", req, new Ack() {
            @Override
            public void call(Object... args) {
                if(generalHandler(args)==200){
                    activity.runOnUiThread(runnable);
                }
            }
        });
    }




    public void removeContributors(int projectId, int[] teamIds, int[] individualIds, final Activity activity, final Runnable runnable){
        HashMap<String, Object> args = new HashMap<>();
        args.put("projectId",projectId);
        args.put("teamIds",teamIds);
        args.put("individualIds",individualIds);

        JSONObject req = RequestFormat.createRequestObj("removeContributors",args);

        Realtime.socket.emit("removeContributors", req, new Ack() {
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

        Realtime.socket.emit("replyIndividualContributorJoinRequest", req, new Ack() {
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

        Realtime.socket.emit("replyTeamContributorJoinRequest", req, new Ack() {
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

        Realtime.socket.emit("getMyProjectDetails", req, new Ack() {
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

        Realtime.socket.emit("deleteProject", req, new Ack() {
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

        Realtime.socket.emit("newTaskset", req, new Ack() {
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

        Realtime.socket.emit("deleteTaskset", req, new Ack() {
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

        Realtime.socket.emit("newTask", req, new Ack() {
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

        Realtime.socket.emit("startTask", req, new Ack() {
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

        Realtime.socket.emit("completeTask", req, new Ack() {
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

        Realtime.socket.emit("changeTaskStatus", req, new Ack() {
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

        Realtime.socket.emit("deleteTask", req, new Ack() {
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
