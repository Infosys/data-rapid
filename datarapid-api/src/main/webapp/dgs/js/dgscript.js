/*
 * Copyright 2018 Infosys Ltd.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//Global Variable contain ID for both parent and child row
var row_container_array = [ "p0c0" ];
var json = null;
var ajax_count = 0;
var wWidth = $(window).width();
var dWidth = wWidth * 0.5;
var wHeight = $(window).height();
var dHeight = wHeight * 0.65;
var userList;
var loggedInUserId;

function removeElementFromArray(index) {
	//console.log("removeElementFromArray");
	if (index > -1) {
		row_container_array.splice(index, 1);
	}
}

function getParentIds() {
	//console.log("getParentIds");
	var parentIds = [];
	for (var i = 0; i < row_container_array.length; i++) {
		var str = row_container_array[i];
		var firstpart = str.split("c");
		// console.log(firstpart[0].substr(1));
		parentIds.push(firstpart[0].substr(1))
	}
	jQuery.unique(parentIds);
	// console.log(parentIds);
	return parentIds;

}
//search particular string in array
function searchStringInArray(str, array) {
	//console.log("searchStringInArray");
	for (var j = 0; j < array.length; j++) {
		if (array[j].match(str))
			return j;
	}
	return -1;
}

$(document).ready(function() {
	//console.log("ready");
	
	

	//console.log("Fetch username");
	getConfigDetails();	
	var serviceUrl = "rest/service/getloggedinuser";
	$.ajax({
		type : 'GET',
		url : serviceUrl,
		dataType : "json",
		success : function(data) {						
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				message = data.message;
				//console.log(message);
				loggedInUserId = data.downloadURL;
				//console.log("loggedInUserId : "+loggedInUserId);
				$('#userNameLabel').text(loggedInUserId);
				if(loggedInUserId != "admin"){
					$('#usersList').css("display","none");
					$('#rolesList').css("display","none");
				}
				
			} else {
				console.log("errorcode part");

			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			//console.log("message: "+message);

		}
	});
	$('#centerContent').html('');
	$('input:radio[id="createNewData"]').prop('checked', true);	
	
	showCreateDataSet();
	//$("#defineData a").addClass("activeMainMenuClass");
	//$('#defineData').css('background-color','#22497D');
	$("input[name=radioSelection]:radio").click(function () {
        if ($('input[name=radioSelection]:checked').val() == "createNewData") {
        	//alert("createNewData");
			showCreateDataSet();
        }else if ($('input[name=radioSelection]:checked').val() == "loadFromRecent") {
        	//alert("loadFromRecent");
			showLoadFromRecent();
        }
	});
	$("#mainMenu li").click(function() {
		var mainMenuId = $(this).attr('id');
		//alert("mainMenu click function");
		/*alert("mainMenuId : " + mainMenuId);*/
		if (mainMenuId == "defineData") {
			$('#dataSetRadioBtns').show();
			$('#centerContent').html('');
			$('input:radio[id="createNewData"]').prop('checked', true);
			showCreateDataSet();
		} else if (mainMenuId == "setting") {
			/* alert("setting if condition"); */
			$('#dataSetRadioBtns').hide();
			$('#centerContent').load('settings.html',function(){
				//fetchRole();
				//$(".settingsPage").addClass("disabledElements");
				$(".settingsPage :input").prop("disabled", true);				
				roleSettings();
			});
		} else if (mainMenuId == "myAcct") {
			/* alert("my account if condition"); */
			$('#dataSetRadioBtns').hide();
			$('#centerContent').load('myAccount.html',function(){
				/*e.preventDefault();*/
				//$("#accountPage").addClass("disabledElements");
				/*$('[data-toggle="tooltipMessage"]').tooltip();*/
				fetchUser();
			});
		} else if (mainMenuId == "usersList") {
			$('#dataSetRadioBtns').hide();
			/* $('#centerContent').load('usersList.html'); */
			$('#centerContent').load('usersList.html',function () {
				//console.log("userList loaded");
				$('#usersDialog').css("display","none");
				loadUsersList();
				$("#centerContent").on('click', '#editUserBtn', function(e){
					e.preventDefault();
					var count =1;
					var tdValues = [];
					$(this).closest('tr').find('td').each(function() {
						var textval = $(this).text(); // this will be the text of each <td>
						tdValues[count]=textval;
						count++;
						if(count>4) {return false;}
					});				    
					/*alert("tdValues[1]:" +tdValues[1]);
					alert("tdValues[2]:" +tdValues[2]);
					alert("tdValues[3]:" +tdValues[3]);
					alert("tdValues[4]:" +tdValues[4]);*/
					$('#usersDialog').css('overflow','hidden'); //to hide the scroll bar
					$('#usersDialog').dialog({
						title:"User Details",
						modal:true,
						width: dWidth,
						height: dHeight,
						draggable: false,
						//resizable: false,
						buttons:{
							'Update':function(){
								/*AJAX call to call create user service*/								
								updateUser();
								$('#usersDialog').dialog('close');
							},
							'Cancel':function(){
								$('#usersDialog').dialog('close');
							}
						},
						open : function () {							
							$("#inputUserName").val(tdValues[1]);
							$("#inputUserDesc").val(tdValues[2]);
							$("#inputCreDate").val(tdValues[3]);
							$("#inputCreBy").val(tdValues[4]);
						}
					});

				});
				$("#centerContent").on('click', '#delUserBtn', function(){
					//alert("delete");
					var countDel =1;
					var tdUserValue = [];
					var tempUser;
					if (!confirm("Do you want to delete!")){
						return false;
					}
					$(this).closest('tr').find('td').each(function() {
						var textval = $(this).text(); // this will be the text of each <td>
						tdUserValue[countDel]=textval;
						//alert("textval"+textval);
						tempUser=tdUserValue[1];
						countDel++;
						if(countDel>1) {return false;}
					});					
					deleteUser(tempUser);
					return false;
				});
				$("#centerContent").on('click', '#createUserBtn', function(e){
					e.preventDefault();
					$('#usersDialog').css('overflow','hidden'); //to hide the scroll bar
					$('#usersDialog').dialog({
						title:"User Creation",
						modal:true,
						width: dWidth,
						height: dHeight,
						draggable: false,
						//resizable: false,
						buttons:{
							'Create':function(){
								/*AJAX call to call create user service*/

								createUser();
								$('#usersDialog').dialog('close');
								//loadUsersList();
							},
							'Cancel':function(){
								$('#usersDialog').dialog('close');
							}
						},
						open : function() {
							$('#usersDialog').find("input[type=text],input[type=password]").val("");
							
						}
					});
					$('#usersDialog').find('.ui-button').addClass('dialogButton');

					return false;
				});
			});

		}else if(mainMenuId == "rolesList"){
			$('#dataSetRadioBtns').hide();
			$('#centerContent').load('rolesList.html',function(){
				$('#rolesDialog').css("display","none");
			});
			
			loadRolesList();
			$("#centerContent").on('click', '#createRoleBtn', function(e){
				e.preventDefault();

				//$('#rolesDialog').css('overflow','hidden'); //to hide the scroll bar
				//ajax call to fetch list of users 
				createRoleMain();

				return false;
			});
			$("#centerContent").on('click', '#editRoleBtn', function(e){
				e.preventDefault();
				editRoleMain.bind($(this))();
			});
			$("#centerContent").on('click', '#delRoleBtn', function(){
				//alert("delete");
				var countDelR =1;
				var tdRoleValue = [];
				var tempRole;
				if (!confirm("Do you want to delete!")){
					return false;
				}
				$(this).closest('tr').find('td').each(function() {
					var textval = $(this).text(); // this will be the text of each <td>
					tdRoleValue[countDelR]=textval;
					//alert("textval"+textval);
					tempRole=tdRoleValue[1];
					countDelR++;
					if(countDelR>1) {return false;}
				});					
				deleteRole(tempRole);
				$(this).closest('tr').remove();
				return false;
			});
		}

	});


	$(".nav-tabs a").click(function() {
		$(this).tab('show');
	});
	$('.nav-tabs a').on('shown.bs.tab', function(event) {
		var x = $(event.target).text();
		var y = $(event.relatedTarget).text();
		$(".act span").text(x);
		$(".prev span").text(y);
	});

	$("#numRows").keypress(function(e) {
		if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
			$("#errmsg").html("Digits Only").show().fadeOut("slow");
			return false;
		}
	});
	// default call getDataType
	//console.log("get datatype num rows ");
	/* getDataType(0, 0); */
});

function showCreateDataSet(){
	/*alert("create data checked");*/
	$('#centerContent').load('createDataset.html',function(){
		//Initialize array
			row_container_array = [ "p0c0" ];
		// default call getDataType
		//console.log("get datatype diff place ");
		 getDataType(0, 0); 
		$("#centerContent").on('click', '#generateBtnId', function(e){
			e.preventDefault();
			//alert("111111111111111");

			generateDataSet();
			/*jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");*/
			// $('#pageContainer').unmask();
			/*$("#errorID").html(
						"<b>" + "Error in Data Generation : " + "</b>"
						+ message);
				jQuery("#error").modal('show');*/
		});
		$('#downloadJSON').on('click',function(e) {
			downloadDataSet();
		});
				
		$('#saveDataSet').on('click',function(e) {
			//console.log("saveDataSet");
			saveDataSet();
		});	
		$('#filetransfer').on('click',function(e) {
			//console.log("file transfer");
			transferDataSet();
			return false;

		});
		
	});		
}

function loadUsersList(){
	$('#userListTable tbody').empty();
	var userActionsHTML='<div><button type="button" id="editUserBtn"><i class="glyphicon glyphicon-edit"></i></button>';
	userActionsHTML +='<button type="button" id="delUserBtn"><i class="glyphicon glyphicon-trash"></i></button></div>';
	//hardcoded for testing
	//var JSONData='[{"userName":"John1","userDesc":"John Desc1","createdTime":"01-JAN-2016"},{"userName":"Peter","userDesc":"Peter Desc1","createdTime":"01-FEB-2016"},{"userName":"Mark","userDesc":"Mark Desc","createdTime":"01-MAR-2016"}]';
	var trHTML = '';
	/*	var obj = jQuery.parseJSON(JSONData);
	$.each(obj, function (i, item) {
		trHTML += '<tr><td>' + item.userName + '</td><td>' + item.userDesc + '</td><td>' + item.createdTime + '</td><td>'+userActionsHTML+'</td></tr>';
	});	*/		        
	$('#userListTable').append(trHTML);
	getConfigDetails();	
	var serviceUrl = "rest/service/queryusers";
	$.ajax({
		type : 'GET',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			if(data == null) {
				return;
			}
			//console.log(data);
			errorCode = data.errorCode;
			//console.log("errorCode : "+errorCode);
			if (errorCode == null || errorCode == '') {
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				//console.log(data.userInfo);				
				$.each(data.userInfo.userMeta, function (i, item) {				
					//console.log("user: "+ item.userName + ",Desc:" + item.userDesc + ",password:" + item.password);
					trHTML += '<tr><td>' + item.userName + '</td><td>' + item.userDesc + '</td><td>' + item.createdTime+ '</td><td>' + item.createdBy + '</td><td>'+userActionsHTML+'</td></tr>';
				});
				$('#userListTable').append(trHTML);
			} else {
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
			}
		},
		error : function(data) {
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
		}
	});       

	return false;

}
function fetchUser(){
	getConfigDetails();	
	var serviceUrl = "rest/service/queryuser/"+ loggedInUserId;
	$.ajax({
		type : 'GET',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			if (errorCode == null || errorCode == '') {
				if(data == null) {
					return;
				}
				//console.log(data);
				//console.log(data.userInfo);				
				$.each(data.userInfo.userMeta, function (i, item) {				
					//console.log("user: "+ item.userName + ",Desc:" + item.userDesc + ",password:" + item.password);
					$("#inputUserName").val(item.userName);
					$("#inputUserDesc").val(item.userDesc);
					$("#inputPassword").val('');
					$("#inputCreDate").val(item.createdTime);
					$("#inputCreBy").val(item.createdBy);
				});	
				errorCode = data.errorCode;
				$("#centerContent").on('click', '#saveAccntBtn', function(e){
					e.preventDefault();
					//alert("update my account");
					//mandatory check
					/*if ( !($('.accountPage #inputUserDesc').val())) {
						console.log("Inside desc null");
						$('.accountPage #inputUserDesc').tooltip('hide').attr('data-original-title',
								"Please enter this field.").tooltip('fixTitle').tooltip('show');
						$('.accountPage #inputUserDesc').tooltip();
						//$('.accountPage #inputUserDesc').attr('data-original-title',"Please enter this field.").tooltip('fixTitle').tooltip('show');
						$('.accountPage #inputUserDesc').tooltip({'trigger':'focus', 'title': 'Password tooltip'});
					}*/
					//myAccountPageValidation();
					updateMyAccount();
					return false;
				});
				$("#centerContent").on('click', '#cancelAccntBtn', function(e){
					e.preventDefault();
					fetchUser();
					return false;
				});				
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");

			} else {
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");

			}
		},
		error : function(data) {
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
		}
	});
	return false;
}
/*function myAccountPageValidation(){
	$('#myAccountForm').formValidation({
        framework: 'bootstrap',
        icon: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        err: {
            // You can set it to popover
            // The message then will be shown in Bootstrap popover
            container: 'tooltip'
        },
        fields: {
        	inputUserDesc: {
                row: '.col-xs-4',
                validators: {
                    notEmpty: {
                        message: 'User Description is required'
                    }
                }
            },
            inputPassword: {
                row: '.col-xs-4',
                validators: {
                    notEmpty: {
                        message: 'The password is required'
                    }
                }
            }
        }
    });
}*/
function createUser(){
	//code to call create user service
	//console.log("createUser function");
	getConfigDetails();	
	var serviceUrl = "rest/service/createuser";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		data : userFormToJSON(),
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("success");
				loadUsersList();
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "User Creation Successful : "
						+ "</b>" + message);
				jQuery("#success").modal('show');
			} else {
				//console.log("errorcode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				/*$("#errorID").html(
						"<b>" + "Error in User Creation : "
						+ "</b>" + message);*/
				//need to chk
				$("#errorID").html(
						"<b>" + "Error in User Creation : "
						+ message+"</b>");
				jQuery("#error").modal('show');
			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			//console.log("message: "+message);
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			$("#errorID").html(
					"<b>" + "Error in User Creation : "
					+ "</b>" + message);
			jQuery("#error").modal('show');
		}
	});

	return false;	
}
function myAccountDataToJSON(){
	var userNameValue = $(".accountPage #inputUserName").val();
	var userDescValue = $(".accountPage #inputUserDesc").val();
	var userPwdValue = $(".accountPage #inputPassword").val();
	var userCreDateValue = $(".accountPage #inputCreDate").val();
	var userCreByValue = $(".accountPage #inputCreBy").val();
	//console.log("userName"+userNameValue+",userDesc"+userDescValue+",createdBy"+userCreByValue+",createdTime"+userCreDateValue+",updatedBy"+""+",updatedTime"+""+",password"+userPwdValue);
	return JSON.stringify({"userInfo":{"userMeta":[{"userName":userNameValue,"userDesc":userDescValue,"createdBy":userCreByValue,"createdTime":userCreDateValue,"updatedBy":"","updatedTime":"","password":userPwdValue}]}});

}
function updateMyAccount(){
	//code to update my account
	//console.log("updateUser function");
	getConfigDetails();	
	var serviceUrl = "rest/service/updateuser";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",		
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		data : myAccountDataToJSON(),
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("success");	
				fetchUser();
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "User updation Successful : "
						+ "</b>" + message);
				jQuery("#success").modal('show');
			} else {
				//console.log("errorcode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				// $('#pageContainer').unmask();
				$("#errorID").html(
						"<b>" + "Error in User Updation : "
						+ "</b>" + message);
				jQuery("#error").modal('show');

			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			console.log("message: "+message);
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			// $('#pageContainer').unmask();
			$("#errorID").html(
					"<b>" + "Error in User Updation : "
					+ "</b>" + message);
			jQuery("#error").modal('show');

		}
	});

	return false;

}

function userFormToJSON() {
	/*return JSON.stringify({"userName":"user5","userDesc":"user5desc","createdBy":"admin","createdTime":"2016-06-07 19:29:11","updatedBy":"admin","updatedTime":"2016-06-08 19:29:11","password":"password"});*/
	var userNameValue = $("#usersDialog #inputUserName").val();
	var userDescValue = $("#usersDialog #inputUserDesc").val();
	var userPwdValue = $("#usersDialog #inputPassword").val();
	var userCreDateValue = $("#usersDialog #inputCreDate").val();
	var userCreByValue = $("#usersDialog #inputCreBy").val();
	//console.log("userName"+userNameValue+",userDesc"+userDescValue+",createdBy"+userCreByValue+",createdTime"+userCreDateValue+",updatedBy"+""+",updatedTime"+""+",password"+userPwdValue);
	//return JSON.stringify({"userInfo":{"userMeta":[{"userName":userNameValue,"userDesc":userDescValue,"createdBy":"admin","createdTime":"2016-06-07 19:29:11","updatedBy":"admin","updatedTime":"2016-06-08 19:29:11","password":userPwdValue}]}});
	return JSON.stringify({"userInfo":{"userMeta":[{"userName":userNameValue,"userDesc":userDescValue,"createdBy":userCreByValue,"createdTime":userCreDateValue,"updatedBy":"","updatedTime":"","password":userPwdValue}]}});
}

function updateUser(){
	//code to call update user service
	//console.log("updateUser function");
	getConfigDetails();	
	var serviceUrl = "rest/service/updateuser";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		data : userFormToJSON(),
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("success");
				loadUsersList();	
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "User updation Successful : "
						+ "</b>" + message);
				jQuery("#success").modal('show');
			} else {
				//console.log("errorcode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				// $('#pageContainer').unmask();
				$("#errorID").html(
						"<b>" + "Error in User Updation : "
						+ "</b>" + message);
				jQuery("#error").modal('show');

			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			//console.log("message: "+message);
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			// $('#pageContainer').unmask();
			$("#errorID").html(
					"<b>" + "Error in User Updation : "
					+ "</b>" + message);
			jQuery("#error").modal('show');
		}
	});

	return false;


}
function deleteUser(tempUser){
	//code to call delete user service
	//console.log("deleteUser function");
	//console.log("tempUser"+tempUser);
	var userInpToDel= JSON.stringify({"userInfo":{"userMeta":[{"userName":tempUser}]}});
	//var userToDel= JSON.stringify({"userInfo":{"userMeta":[{"userName":"user8"}]}});
	//console.log("userInpToDel"+userInpToDel);    
	getConfigDetails();	
	var serviceUrl = "rest/service/deleteuser";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		data : userInpToDel,
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("success");
				loadUsersList();
				$(this).closest('tr').remove();
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "User Deletion Successful : "
						+ "</b>" + message);
				jQuery("#success").modal('show');
			} else {
				//console.log("errorcode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				// $('#pageContainer').unmask();
				$("#errorID").html(
						"<b>" + "Error in User Deletion : "
						+ "</b>" + message);
				jQuery("#error").modal('show');
			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			//console.log("message: "+message);	
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			// $('#pageContainer').unmask();
			$("#errorID").html(
					"<b>" + "Error in User Deletion : "
					+ "</b>" + message);
			jQuery("#error").modal('show');
		}
	});

	return false;
}
function roleSettings(){
	getConfigDetails();	
	var serviceUrl = "rest/service/queryusers";
	$.ajax({
		type : 'GET',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			if(data == null) {
				return;
			}
			//console.log(data);
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("Success");
				//console.log(data.userInfo);
				//$('#groupUsers').empty();
				$('#groupUsers').find('option').remove();
				userList='[';
				$.each(data.userInfo.userMeta, function (i, item) {				
					//console.log("user: "+ item.userName);
					userList += '{"userName":"'+item.userName+'"},';

				});
				userList = userList.slice(0, -1);
				userList+=']';
				//console.log("userList : "+ userList);
				var multiDropDownHTMLfetch = '';
				var obj = jQuery.parseJSON(userList);
				$.each(obj, function (i, item) {
					multiDropDownHTMLfetch += '<option>' + item.userName + '</option>';	
				});	
				//console.log("multiDropDownHTMLfetch: "+multiDropDownHTMLfetch);
				$('#groupUsers').append(multiDropDownHTMLfetch);
				fetchRole();
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				return;
			} else {
				//console.log("ErrorCode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
			}
		},
		error : function(data) {
			//console.log("Error");
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
		}
	});
}
function fetchRole(){
	getConfigDetails();	
	var serviceUrl = "rest/service/queryrole/"+loggedInUserId;
	$.ajax({
		type : 'GET',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			if(data == null) {
				return;
			}
			//console.log(data);
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log(data.roleInfo);
				var roleGroupUsersFetchArray;
				$.each(data.roleInfo.roleMeta, function (i, item) {				
					//console.log("user: "+ item.userName + ",roleName:" + item.roleName + ",roleDesc:" + item.roleDesc);
					//console.log("roleType: "+ item.roleType + ",groupUsers:" + item.groupUsers + ",usageType:" + item.usageType);
					//console.log("startDate: "+ item.startDate + ",endDate:" + item.endDate + ",usageLimit:" + item.usageLimit);
					//console.log("daysLimit: "+ item.daysLimit);
					$("#userName").val(item.userName);
					$("#roleName").val(item.roleName);
					$("#roleDesc").val(item.roleDesc);
					$("#roleType").val(item.roleType);
					//console.log("item.groupUsers : "+item.groupUsers);
					if(item.groupUsers){
						roleGroupUsersFetchArray=item.groupUsers.split(",");
					}
					//console.log("roleGroupUsersFetchArray : "+ roleGroupUsersFetchArray);
					$("#groupUsers").val(roleGroupUsersFetchArray);
					$("#usageType").val(item.usageType);
					$("#startDate").val(item.startDate);
					$("#endDate").val(item.endDate);
					$("#recordCountLimit").val(item.recordCountLimit);
					$("#usageLimit").val(item.usageLimit);
					$("#daysLimit").val(item.daysLimit);
					$(".settingsPage :input").css({"background-color": "#fafafa", "color": "black"})					
				});					
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
			} else {
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
			}
		},
		error : function(data) {
			// console.log(data);
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");

		}
	});
	return false;
}
function loadRolesList(){
	$('#roleListTable tbody').empty();
	var roleActionsHTML='<div><button type="button" id="editRoleBtn"><i class="glyphicon glyphicon-edit"></i></button>';
	roleActionsHTML +='<button type="button" id="delRoleBtn"><i class="glyphicon glyphicon-trash"></i></button></div>';

	//hardcoded for testing
	var temp1="1111";
	var temp2="2222";
	//var JSONData='[{"userName":"John1","userDesc":"John Desc1","createdTime":"01-JAN-2016"},{"userName":"Peter","userDesc":"Peter Desc1","createdTime":"01-FEB-2016"},{"userName":"Mark","userDesc":"Mark Desc","createdTime":"01-MAR-2016"}]';
	var trHTML = '';
	/*	var obj = jQuery.parseJSON(JSONData);
	$.each(obj, function (i, item) {
		trHTML += '<tr><td>' + item.userName + '</td><td>' + item.userDesc + '</td><td>' + item.createdTime + '</td><td>'+userActionsHTML+'</td></tr>';
	});	*/		        
	$('#roleListTable').append(trHTML);
	getConfigDetails();	
	var serviceUrl = "rest/service/queryroles";
	$.ajax({
		type : 'GET',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			if(data == null) {
				return;
			}
			//console.log(data);
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("Success");
				//console.log(data.roleInfo);	
				$.each(data.roleInfo.roleMeta, function (i, item) {				
					//console.log("user: "+ item.userName + ",Desc:" + item.roleName + ",password:" + item.roleDesc);
					//console.log("roleType: "+ item.roleType + ",groupUsers:" + item.groupUsers + ",usageType:" + item.usageType);
					var roleNameText = getRoleName(item.roleName);
					//console.log("roleNameText:"+roleNameText);
					
					trHTML += '<tr>'
					trHTML += '<td data-roleType="'+item.roleType+'" data-roleNameValue="'+item.roleName+'" data-groupUsers="'+item.groupUsers+'" data-usageType="'+item.usageType+'" data-startDate="'+item.startDate+'" data-endDate="'+item.endDate+'" data-usageLimit="'+item.usageLimit+'" data-daysLimit="'+item.daysLimit+'" data-recordCountLimit="'+item.recordCountLimit+'">';
					trHTML += item.userName + '</td>';
					trHTML += '<td>' + roleNameText + '</td>';
					trHTML += '<td>'+item.roleDesc + '</td>';
					trHTML += '<td>'+roleActionsHTML+'</td></tr>';
				});
				//console.log(trHTML);
				$('#roleListTable').append(trHTML);
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
			} else {
				//console.log("Error Code part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
			}
		},
		error : function(data) {
			//console.log(data);
			//console.log("Error");
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
		}
	});
	return false;	
}
function getRoleName(roleN){
	switch(roleN) {
	case "ROLE_ADMIN":
		roleText = "Admin";
		break;
	case "ROLE_NORMAL_USER":
		roleText = "Normal User";
		break;
	case "ROLE_GROUP_USER":
		roleText = "Group User";
		break;  

	default:
		roleText = "Invalid Role";
  }
	return roleText;
}
function createRoleMain(){	
	getConfigDetails();	
	var serviceUrl = "rest/service/queryusers";
	$.ajax({
		type : 'GET',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			if(data == null) {
				return;
			}
			//console.log(data);
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("Success");
				//console.log(data.userInfo);	
				userList='[';
				$.each(data.userInfo.userMeta, function (i, item) {				
					//console.log("user: "+ item.userName);
					userList += '{"userName":"'+item.userName+'"},';

				});
				userList = userList.slice(0, -1);
				userList+=']';
				//console.log("userList : "+ userList);
				openCreateRoleDialog();
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				return;
			} else {
				//console.log("ErrorCode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
			}
		},
		error : function(data) {
			//console.log("Error");
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
		}
	});       

	return false;	
}
function openCreateRoleDialog(){
	//console.log("openCreateRoleDialog userList : "+ userList);
	$('#groupUsers').find('option').remove();
	var multiDropDownHTML = '';
	var obj = jQuery.parseJSON(userList);
	$.each(obj, function (i, item) {
		multiDropDownHTML += '<option>' + item.userName + '</option>';
	});	
	//console.log("multiDropDownHTML: "+multiDropDownHTML);
	$('#rolesDialog').dialog({
		title:"Role Creation",
		modal:true,
		width: dWidth,
		height: dHeight,
		draggable: false,
		//resizable: false,
		buttons:{
			'Create':function(){
				/*AJAX call to call create role service*/							
				createRole();
				$('#rolesDialog').dialog('close');
				//loadRolesList();
			},
			'Cancel':function(){
				$('#rolesDialog').dialog('close');
			}
		},
		open:function(){
			$('#rolesDialog').find("input[type=text]").val("");
			$("#rolesDialog option:selected").removeAttr("selected");
			$('#groupUsers').append(multiDropDownHTML);
			$('#rolesDialog #startDate').datepicker({
				dateFormat: "yy-mm-dd"
			});
			$('#rolesDialog #endDate').datepicker({
				dateFormat: "yy-mm-dd"
			});
			$("#rolesDialog #groupUsers").prop("disabled", true);			
			disableUsageType();
			
			$('#rolesDialog #roleName').change( function() {
				if($('#rolesDialog #roleName').val() == "ROLE_GROUP_USER"){
					$("#rolesDialog #groupUsers").prop("disabled", false);
				}else{
					$("#rolesDialog #groupUsers option:selected").removeAttr("selected");
					$("#rolesDialog #groupUsers").prop("disabled", true);
				}
			});
			
			$('#rolesDialog #usageType').change( function() {
				changeUsageType();
			});	
		}
	});
	$('#rolesDialog').find('.ui-button').addClass('dialogButton');
	return false;
}
function disableUsageType(){	
	$("#rolesDialog #startDate").prop("disabled", true);
	$("#rolesDialog #endDate").prop("disabled", true);
	$("#rolesDialog #recordCountLimit").prop("disabled", true);
	$("#rolesDialog #usageLimit").prop("disabled", true);
	$("#rolesDialog #daysLimit").prop("disabled", true);
}
function clearUsageTypes(){
	$("#rolesDialog #startDate").val('');
	$("#rolesDialog #endDate").val('');
	$("#rolesDialog #recordCountLimit").val('');
	$("#rolesDialog #usageLimit").val('');
	$("#rolesDialog #daysLimit").val('');
}
function changeUsageType(){
	//console.log("usage type changed");
	if($('#rolesDialog #usageType').val() == "UNLIMITED"){
		clearUsageTypes();
		disableUsageType();
	}else if($('#rolesDialog #usageType').val() == "PERIOD_BASED"){
		clearUsageTypes();
		disableUsageType();
		$("#rolesDialog #startDate").prop("disabled", false);
		$("#rolesDialog #endDate").prop("disabled", false);
	}else if($('#rolesDialog #usageType').val() == "DAYS_BASED"){
		clearUsageTypes();
		disableUsageType();
		$("#rolesDialog #daysLimit").prop("disabled", false);
	}else if($('#rolesDialog #usageType').val() == "RECORD_COUNT_BASED"){
		clearUsageTypes();
		disableUsageType();
		$("#rolesDialog #recordCountLimit").prop("disabled", false);
	}else if($('#rolesDialog #usageType').val() == "SIZE_BASED"){
		clearUsageTypes();
		disableUsageType();
		$("#rolesDialog #usageLimit").prop("disabled", false);
	}
}
function editRoleMain(){
	var $this = $(this);
	var countR =1;
	var tdRoleValues = [];
	var roleTypeData;
	var roleNameDataValue;
	var roleGroupUsers;
	var roleUsageType;
	var roleStartDate;
	var roleEndDate;
	var roleUsageLimit;
	var roleDaysLimit;	
	var roleRecordCountLimit;
	var roleGroupUsersArray;
	$(this).closest('tr').find('td').each(function() {
		var textval = $(this).text(); // this will be the text of each <td>
		tdRoleValues[countR]=textval;
		countR++;
		if(countR>3) {return false;}
	});		
	/*alert( $(this).parent().parent().find('td:first').text());*/ //get the first td
	//$('#rolesDialog').css('overflow','hidden'); //to hide the scroll bar
	//alert( $(this).closest('tr').find('td:first').text());
	roleTypeData = $(this).closest('tr').find('td:first').attr("data-roleType");
	//alert("roleTypeData: "+roleTypeData);
	roleNameDataValue = $(this).closest('tr').find('td:first').attr("data-roleNameValue");
	//console.log("roleNameDataValue: "+roleNameDataValue);
	roleGroupUsers = $(this).closest('tr').find('td:first').attr("data-groupUsers");
	//alert("roleGroupUsers: "+roleGroupUsers);
	if(roleGroupUsers){
		roleGroupUsersArray=roleGroupUsers.split(",");
	}
	//console.log("roleGroupUsersArray : "+ roleGroupUsersArray);
	
	roleUsageType = $(this).closest('tr').find('td:first').attr("data-usageType");
	roleStartDate = $(this).closest('tr').find('td:first').attr("data-startDate");
	roleEndDate = $(this).closest('tr').find('td:first').attr("data-endDate");
	roleUsageLimit = $(this).closest('tr').find('td:first').attr("data-usageLimit");
	roleDaysLimit = $(this).closest('tr').find('td:first').attr("data-daysLimit");	
	roleRecordCountLimit = $(this).closest('tr').find('td:first').attr("data-recordCountLimit");
	getConfigDetails();
	var serviceUrl = "rest/service/queryusers";
	$.ajax({
		type : 'GET',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			if(data == null) {
				return;
			}
			//console.log(data);
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("Success");
				//console.log(data.userInfo);	
				$('#groupUsers').find('option').remove();
				userList='[';
				$.each(data.userInfo.userMeta, function (i, item) {				
					//console.log("user: "+ item.userName);
					userList += '{"userName":"'+item.userName+'"},';

				});
				userList = userList.slice(0, -1);
				userList+=']';
				//console.log("userList : "+ userList);
				var multiDropDownHTMLedit = '';
				var obj = jQuery.parseJSON(userList);
				$.each(obj, function (i, item) {
					multiDropDownHTMLedit += '<option>' + item.userName + '</option>';
				});	
				//console.log("multiDropDownHTMLedit: "+multiDropDownHTMLedit);
				$('#rolesDialog').dialog({
					title:"User Details",
					modal:true,
					width: dWidth,
					height: dHeight,
					draggable: false,
					//resizable: false,
					buttons:{
						'Update':function(){
							//AJAX call to call create user service							
							updateRole();
							$('#rolesDialog').dialog('close');
						},
						'Cancel':function(){
							$('#rolesDialog').dialog('close');
						}
					},
					open : function () {                        
						$("#userName").val(tdRoleValues[1]);
						$("#roleName").val(roleNameDataValue);
						$("#roleDesc").val(tdRoleValues[3]);
						$("#roleType").val(roleTypeData);
						$('#groupUsers').append(multiDropDownHTMLedit);
						//$("#groupUsers").val(roleGroupUsersArray);
						//$("#groupUsers").multiselect("refresh");						
						$("#usageType").val(roleUsageType);
						$("#startDate").val(roleStartDate);
						$("#endDate").val(roleEndDate);
						$("#recordCountLimit").val(roleRecordCountLimit);
						$("#usageLimit").val(roleUsageLimit);
						$("#daysLimit").val(roleDaysLimit);
						$('#rolesDialog #startDate').datepicker({
							dateFormat: "yy-mm-dd"
						});
						$('#rolesDialog #endDate').datepicker({
							dateFormat: "yy-mm-dd"
						});
						
						//disableUsageType();
						if($('#rolesDialog #roleName').val() == "ROLE_GROUP_USER"){
							$("#rolesDialog #groupUsers").prop("disabled", false);
							$("#groupUsers").val(roleGroupUsersArray);
						}else{
							$("#rolesDialog #groupUsers option:selected").removeAttr("selected");
							$("#rolesDialog #groupUsers").prop("disabled", true);
						}
						if($('#rolesDialog #usageType').val() == "UNLIMITED"){
							disableUsageType();
						}else if($('#rolesDialog #usageType').val() == "PERIOD_BASED"){
							disableUsageType();
							$("#rolesDialog #startDate").prop("disabled", false);
							$("#rolesDialog #endDate").prop("disabled", false);
						}else if($('#rolesDialog #usageType').val() == "DAYS_BASED"){
							disableUsageType();
							$("#rolesDialog #daysLimit").prop("disabled", false);
						}else if($('#rolesDialog #usageType').val() == "RECORD_COUNT_BASED"){
							disableUsageType();
							$("#rolesDialog #recordCountLimit").prop("disabled", false);
						}else if($('#rolesDialog #usageType').val() == "SIZE_BASED"){
							disableUsageType();
							$("#rolesDialog #usageLimit").prop("disabled", false);
						}
						
						$('#rolesDialog #roleName').change( function() {
							if($('#rolesDialog #roleName').val() == "ROLE_GROUP_USER"){
								$("#rolesDialog #groupUsers").prop("disabled", false);
							}else{
								$("#rolesDialog #groupUsers option:selected").removeAttr("selected");
								$("#rolesDialog #groupUsers").prop("disabled", true);
							}
						});
						
						$('#rolesDialog #usageType').change( function() {
							changeUsageType();
						});	
						
					}
				});
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				return;
			} else {
				//console.log("ErrorCode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
			}
		},
		error : function(data) {
			//console.log("Error");
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
		}
	}); 
	return false;
}
function roleFormToJSON() {
	var roleInfoInputData;
	var userNameValue = $("#rolesDialog #userName").val();
	var roleNameValue = $("#rolesDialog #roleName").val();
	var roleDescValue = $("#rolesDialog #roleDesc").val();
	var roleTypeValue = $("#rolesDialog #roleType").val(); //var roleType field made hidden in Frontend
	//var roleTypeValue = "test";
	var groupUsersValue = $("#rolesDialog #groupUsers").val();
	if(groupUsersValue){
		groupUsersValue = groupUsersValue.toString();
	}	
	//alert("group"+ $("#rolesDialog #groupUsers").val());
	//var groupUsersValue = "group";
	var usageTypeValue = $("#rolesDialog #usageType").val();
	var startDateValue = $("#rolesDialog #startDate").val();
	var endDateValue = $("#rolesDialog #endDate").val();
	var recordCountLimitValue = $("#rolesDialog #recordCountLimit").val();
	var usageLimitValue = $("#rolesDialog #usageLimit").val();
	var daysLimitValue = $("#rolesDialog #daysLimit").val();
	//roleInfoInputData = {"roleInfo":{"roleMeta":[{"userName":"user5","roleName":"ROLE_NORMAL_USER","roleDesc":"user5 role descupd","roleType":"individual","groupUsers":"group","usageType":"unlimited","startDate":"2016-06-01","endDate":"2016-07-01","recordCountLimit":"10000","usageLimit":"1000000","daysLimit":"30"}]}};
	roleInfoInputData = {"roleInfo":{"roleMeta":[{"userName":userNameValue,"roleName":roleNameValue,"roleDesc":roleDescValue,"roleType":roleTypeValue,"groupUsers":groupUsersValue,"usageType":usageTypeValue,"startDate":startDateValue,"endDate":endDateValue,"recordCountLimit":recordCountLimitValue,"usageLimit":usageLimitValue,"daysLimit":daysLimitValue}]}};
	//console.log("userName"+userNameValue+",roleName"+roleNameValue+",roleDesc"+roleDescValue+",roleType"+roleTypeValue+",groupUsers"+groupUsersValue+",usageType"+usageTypeValue+",startDate"+startDateValue+",endDate"+endDateValue+",recordCountLimit"+recordCountLimitValue+",usageLimit"+usageLimitValue+",daysLimit"+daysLimitValue);
	//return JSON.stringify(roleInfoInputData); //commented as it is not working
	return JSON.stringify({"roleInfo":{"roleMeta":[{"userName":userNameValue,"roleName":roleNameValue,"roleDesc":roleDescValue,"roleType":roleTypeValue,"groupUsers":groupUsersValue,"usageType":usageTypeValue,"startDate":startDateValue,"endDate":endDateValue,"recordCountLimit":recordCountLimitValue,"usageLimit":usageLimitValue,"daysLimit":daysLimitValue}]}});

}
function createRole(){
	//code to call create role service
	//console.log("createRole function");
	getConfigDetails();	
	var serviceUrl = "rest/service/createrole";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		data : roleFormToJSON(),
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("success");
				loadRolesList();
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "Role Creation Successful : "
						+ "</b>" + message);
				jQuery("#success").modal('show');
			} else {
				//console.log("errorcode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#errorID").html(
						"<b>" + "Error in Role Creation : "
						+ "</b>" + message);
				jQuery("#error").modal('show');

			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			//console.log("message: "+message);
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			$("#errorID").html(
					"<b>" + "Error in Role Creation : "
					+ "</b>" + message);
			jQuery("#error").modal('show');
		}
	});

	return false;	
}
function updateRole(){
	//code to call update role service
	//console.log("updateRole function");
	getConfigDetails();	
	var serviceUrl = "rest/service/updaterole";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		data : roleFormToJSON(),
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("success");
				loadRolesList();	
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "Role Updation Successful : "
						+ "</b>" + message);
				jQuery("#success").modal('show');
			} else {
				//console.log("errorcode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#errorID").html(
						"<b>" + "Error in Role Updation : "
						+ "</b>" + message);
				jQuery("#error").modal('show');
			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			//console.log("message: "+message);
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			$("#errorID").html(
					"<b>" + "Error in Role Updation : "
					+ "</b>" + message);
			jQuery("#error").modal('show');
		}
	});

	return false;


}
function deleteRole(tempUser){
	//code to call delete role service
	//console.log("deleteRole function");
	//console.log("tempUser"+tempUser);
	//alert("tempUser"+tempUser);
	var userInpToDelRole= JSON.stringify({"roleInfo":{"roleMeta":[{"userName":tempUser}]}});
	//var userToDel= JSON.stringify({"userInfo":{"userMeta":[{"userName":"user8"}]}});
	//console.log("userInpToDelRole"+userInpToDelRole);    
	getConfigDetails();	
	var serviceUrl = "rest/service/deleterole";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		/* data : JSON.stringify($('form').serializeObject()), */
		data : userInpToDelRole,
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("success");
				//$(this).closest('tr').remove();
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "Role Deletion Successful : "
						+ "</b>" + message);
				jQuery("#success").modal('show');
			} else {
				//console.log("errorcode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#errorID").html(
						"<b>" + "Error in Role Deletion : "
						+ "</b>" + message);
				jQuery("#error").modal('show');
			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			//console.log("message: "+message);	
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			$("#errorID").html(
					"<b>" + "Error in Role Deletion : "
					+ "</b>" + message);
			jQuery("#error").modal('show');
		}
	});

	return false;
}
function showLoadFromRecent(){
	$('#centerContent').load('loadRecent.html',function(){
		//Clear contents of array
		row_container_array = [];

		loadRecentList();
		$("#centerContent").on('click', '#editRecentBtn', function(e){
			e.preventDefault();
			//jQuery("#loading").modal('show');
			//$("#wait").css("display", "block");
			var configJSONDataString=[];
			//logic to fetch data
			//queryDataSet.bind($(this))();
			var countR=1;
			var textval;
			var tdValues=[];
			
			$(this).closest('tr').find('td').each(function() {
				textval = $(this).text(); // this will be the text of each <td>
				//alert("textval : "+textval);
				tdValues[countR]=textval;
				countR++;
				if(countR>3) {return false;}
			});
			//alert("Val :"+$(this).closest('tr').find('td:first').attr("data-configJSON"));
			configJSONDataString = unescape($(this).closest('tr').find('td:first').attr("data-configJSON"));
			//console.log("configJSONDataString :"+configJSONDataString);
			
			$('#dataSetContainer').load('createDataset.html',function(){
				//$('#loadListSection').css("display","none");				
				$('#loadRecentTopSection').css("display","none");
				//console.log("configJSONDataString after loading :"+configJSONDataString);
				$('input:radio[id="createNewData"]').prop('checked', true);
				loadUpdateDataSet(configJSONDataString);			
				//jQuery("#loading").modal('hide');
				//$("#wait").css("display", "none");
				$('#saveDataSet').on('click',function(e) {
					//console.log("saveUpdatedDataSet");
					saveUpdatedDataSet();
				});	
				$('#downloadJSON').on('click',function(e) {
					downloadDataSet();
				});
				$('#filetransfer').on('click',function(e) {
					transferDataSet();
				});
			});


			return false;
		});
		$("#centerContent").on('click', '#delRecentBtn', function(){
			//alert("delete");
			var countRecDel =1;
			var tdRecValue = [];
			var tempFile;
			if (!confirm("Do you want to delete!")){
				return false;
			}
			$(this).closest('tr').find('td').each(function() {
				var textval = $(this).text(); // this will be the text of each <td>
				tdRecValue[countRecDel]=textval;
				//alert("textval"+textval);
				tempFile=tdRecValue[1];
				countRecDel++;
				if(countRecDel>1) {return false;}
			});
			deleteDataSet(tempFile);
			return false;
		});

	});
}
function queryDataSet(){
	var countR=1;
	var textval;
	var tdValues=[];
	
	$(this).closest('tr').find('td').each(function() {
		textval = $(this).text(); // this will be the text of each <td>
		//alert("textval : "+textval);
		tdValues[countR]=textval;
		countR++;
		if(countR>3) {return false;}
	});
	alert("Val :"+$(this).closest('tr').find('td:first').attr("data-configJSON"));
	configJSONDataString = $(this).closest('tr').find('td:first').attr("data-configJSON");
	//console.log("configJSONDataString :"+configJSONDataString);
}

function loadRecentList(){
	$('#loadListTable tbody').empty();
	var trRecentHTML = '';
	var configJSONString;
	var recentBtnHTML='<div><button type="button" id="editRecentBtn"><i class="glyphicon glyphicon-edit"></i></button>';
	recentBtnHTML +='<button type="button" id="delRecentBtn"><i class="glyphicon glyphicon-trash"></i></button></div>';
	getConfigDetails();	
	var serviceUrl = "rest/service/querydatasets";
	$.ajax({
		type : 'GET',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		success : function(data) {
			if(data == null) {
				return;
			}
			//console.log(data);
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("Success");
				//console.log(data.dataSetConfigurations);	
				$.each(data.dataSetConfigurations, function (i, item) {				
					//console.log("fileName: "+ item.fileName + ",fileType:" + item.fileType + ",numberOfRows:" + item.numberOfRows + ",userName:" + item.userName + ",createdTime:" + item.createdTime);
					//$.each(item.jobConfiguration, function (i, item1) {
					configJSONString = JSON.stringify(item.jobConfiguration);
					//console.log("config: "+ configJSONString);
					trRecentHTML += '<tr>'
						//trRecentHTML += '<td data-configJSON="'+configJSONString+'">';
					trRecentHTML += '<td data-configJSON='+escape(configJSONString)+'>';
					trRecentHTML += item.jobConfiguration.fileName + '</td>';
					trRecentHTML += '<td>' + item.userName + '</td>';
					trRecentHTML += '<td>'+ item.createdTime + '</td>';
					//trRecentHTML += '<td></td><td></td>';
					trRecentHTML += '<td>'+recentBtnHTML+'</td></tr>';
					//});
				});
				//console.log(trRecentHTML);
				$('#loadListTable').append(trRecentHTML);
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
			} else {
				//console.log("Error Code part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
			}
		},
		error : function(data) {
			//console.log(data);
			//console.log("Error");
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
		}
	});
	
	return false;
	
}

function loadUpdateDataSet(configJSONDataString){
	var JSONformObj = jQuery.parseJSON(configJSONDataString);
	$("#jobName").val(JSONformObj.fileName);
	$("#fileType").val(JSONformObj.fileType);
	$("#numRows").val(JSONformObj.numberOfRows);
	var dataSetVarUpd = 1;
	//$('#addr0').remove();
	$.each(JSONformObj.fileInfo.listOfColumns, function (i, item1) {					
		//console.log("values >>>>"+item1.values);
		//console.log("type >>>>"+item1.type);
		//console.log("valueType >>>>"+item1.valueType);
		//console.log("columnName >>>>"+item1.columnName);
		//console.log("dataType >>>>"+item1.dataType);
		//console.log("order >>>>"+item1.order);
		//console.log("------------");
		//Begin - Add main multirec//
		//console.log("dataSetVarUpd value before adding row :"+dataSetVarUpd);
		
		$('#addr' + dataSetVarUpd)
		.html(
				"<th scope='row' id='order' class='order' value='"+ item1.order +"' name='dataSetOrder'>"
				+ (dataSetVarUpd + 1)
				+ "</th>"
				+ "<td><input name='columnName"
				+ dataSetVarUpd
				+ "' id='columnName"
				+ dataSetVarUpd
				+ "' onChange='updateSourceHeader("
				+ dataSetVarUpd
				+ ")' type='text' placeholder='Column Name' class='colTitle form-control input-md' value='"+ item1.columnName +"'/> </td>"
				+ "<td><select name='dataType"
				+ dataSetVarUpd
				+ "' id='dataType"
				+ dataSetVarUpd
				+ "' class='dataType' onChange='getDataType("
				+ dataSetVarUpd
				+ ",0);'> <option value='alphanumeric'>Alpha Numeric</option> <option value='americancard'>American Card</option> <option value='city'>City</option> <option value='country'>Country</option> <option value='cvv'>CVV</option> <option value='date'>Date</option> <option value='defaultset'>Default Set</option> <option value='digitformat'>Digit format</option> <option value='DiscoveryCreditCard'>Discovery Credit Card</option> <option value='emailaddress'>Email Address</option> <option value='firstname'>First Name</option> <option value='floatrange'>Float Range</option> <option value='guid'>Guid</option> <option value='hexadecimalcode'>Hexa Decimal Code</option> <option value='hexcolors'>Hex Colors</option> <option value='IncrementalUniqueValues'>Incremental Unique Values</option> <option value='integerrange'>Integer Range</option> <option value='imei'>IMEI Number</option> <option value='ipaddress'>IP Address</option> <option value='lastname'>Last Name</option> <option value='location'>Location</option> <option value='macaddress'>MAC Address</option> <option value='mastercardnumber'>Mastercard Number</option> <option value='numberformat'>Number Format</option> <option value='password'>Password</option> <option value='phonenumber'>Phone Number</option> <option value='phonenumberwithext'>Phone Number WithExt</option> <option value='rcommand'>RCommand</option> <option value='ssn'>SSN</option> <option value='state'>State</option> <option value='timestamp'>Timestamp</option> <option value='uniquevalues'>Unique Values</option> <option value='userdefinedregex'>User Defined Regex Pattern</option> <option value='visacreditcardnumber'>VisaCreditCard Number</option> <option value='zipcode'>ZipCode</option> </select></td>"
				+ "<td><input type='text' name='datavalues"
				+ dataSetVarUpd
				+ "' id='datavalues"
				+ dataSetVarUpd
				+ "' placeholder='Enter Values' class='datavalues form-control input-md' onchange='getDataTypeFromValue("
				+ dataSetVarUpd
				+ ",0)' value='"+item1.values+"'></td>"
				+ "<td><span id='addrAdvBtn"
				+ dataSetVarUpd
				+ "' data-toggle='modal' data-target='#addr"
				+ dataSetVarUpd
				+ "myModalNorm' class='glyphicon glyphicon-new-window popupBtn'  aria-hidden='true'></span> "
				+ "<!--span class='glyphicon glyphicon-pushpin bucketingIcon' show='bucketing'></span>-->"
				+ "</td>"
				+ "<td><button type='button'  onclick='deleteRow("
				+ dataSetVarUpd
				+ ")' class='btn btn-sm btn-danger'> <i class='glyphicon glyphicon-remove-circle'></i> </button>&nbsp;"
				+ "<button type='button'  onclick='addRow()' class='btn btn-sm btn-success'> <i class='glyphicon glyphicon-plus'></i> </button></td>"

		);

		$('#tabCell').append('<tr id="addr' + (dataSetVarUpd +1) + '"></tr>');
		$('#dataType'+ dataSetVarUpd).val(item1.dataType);
		//End - Add main multirec//
		//console.log("bucketedColumnHeader >>>>"+item1.bucketedColumn.bucketedColumnHeader);
		
		var updatePopupCnt=0;
		var appendModal = '<div class="modal fade in" id="addr'
			+ dataSetVarUpd
			+ 'myModalNorm" tabindex="-1" role="dialog"'
			+ 'aria-labelledby="myModalLabel" aria-hidden="true">'
			+ '<div class="modal-dialog modal-lg">'
			+ '<div class="modal-content">'
			+ '<div class="modal-header">'
			+ '<button type="button" class="close"'
			+ 'data-dismiss="modal">'
			+ '<span aria-hidden="true">&times;</span>'
			+ '<span class="sr-only">Close</span>'
			+ '</button>'
			+ '<h5 class="modal-title" id="myModalLabel"><b>Segment - Column Mapper</b></h5>'
			+ '</div>'
			+ '<div class="modal-body">'
			+ '<table>'
			+ '<div class="firstDiv">'
			+ '<td><div class="srcClnm"><label>Select Source Column &nbsp;&nbsp;&nbsp;</label> </td><td><input type="text" id="srcClnm'
			+ dataSetVarUpd
			+ '" onchange="updateHeader('
			+ dataSetVarUpd
			+ ')"class="form-control sourceClnHdrInp" placeholder="Source Column Header"</div></td>'
			+ '<td><div class="destClnHdr"> <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Destination Column Header &nbsp;&nbsp;&nbsp;</label></td><td><input type="text" id="destClnHdr'
			+ dataSetVarUpd
			+ '" class="form-control destClnHdrInp" placeholder="Destination Column Header"/> </div> </td>'
			+ '</div>'
			+ '</table>'
			+ '<div class="tableDiv">'
			+ '<table class="table table-bordered table-striped" id="advancedtabCell'
			+ dataSetVarUpd
			+ '">'
			+ '<thead>'
			+ '<br/>'
			+ '<tr>'
			+ '<th> <label>Values From Set</label> </th>'
			+ '<th> <label>Data Type</label>  </th>'
			+ '<th width="5%"> <label>Rows(Percentage)<input type="hidden" id="noOfRows'
			+ dataSetVarUpd
			+ '" value="100" ></label> </th>'
			+ '<th> <label>Values</label> </th>'
			+ '<th><button type="button" id="popupadd_row'
			+ dataSetVarUpd
			+ '"'
			+ 'class="btn btn-sm btn-success" onClick="popUpAddRow('
			+ dataSetVarUpd
			+ ');">'
			+ '<i class="glyphicon glyphicon-plus"></i>Add Row'
			+ '</button></th>'
			+ '</tr>'
			+ '</thead>'
			+ '<tbody>';
		appendModal +='</tbody>'
			+ '</table>'
			+ '</div>'
			+ '</div>'
			+ '<div class="modal-footer">'
			+ '<button type="button" class="btn btn-primary" data-dismiss="modal">'
			+ 'Save' + '</button>' +
			'</div>' + '</div>' + '</div>' + '</div>';
		$('#allmodal').append(appendModal);
		$.each(item1.bucketedColumn.columnMapper, function (i, item2) {	
			//console.log("columnMapper type >>>>"+item2.type);
			//console.log("columnMapper dataType >>>>"+item2.dataType);
			//console.log("columnMapper values >>>>"+item2.values);
			//console.log("columnMapper percentageOfrows >>>>"+item2.percentageOfrows);
			//console.log("columnMapper valuesFromSet >>>>"+item2.valuesFromSet);
			//console.log("columnMapper valueType >>>>"+item2.valueType);
			//console.log("------------");
			//console.log("updatePopupCnt :"+updatePopupCnt);
			//Begin - Add modal multirec
			
			var modalHTML = '<tr id="popupadd_row'
				+ dataSetVarUpd
				+ '_advancedaddr'+updatePopupCnt+'" class="trow">'
				+ '<td><select style="height: 33px; width: 130px;" name="columnName_parent'
				+ dataSetVarUpd
				+ '_child'+updatePopupCnt+'" id="columnName_parent'
				+ dataSetVarUpd
				+ '_child'+updatePopupCnt+'"><select></td>'
				+ '<td><select name="dataType_parent'
				+ dataSetVarUpd
				+ '_child'+updatePopupCnt+'" id="dataType_parent'
				+ dataSetVarUpd
				+ '_child'+updatePopupCnt+'" class="dataType" onChange="changetextboxpopup('
				+ dataSetVarUpd
				+ ',0);"> <option value="alphanumeric">Alpha Numeric</option> <option value="americancard">American Card</option> <option value="city">City</option> <option value="country">Country</option> <option value="cvv">CVV</option> <option value="date">Date</option> <option value="defaultset">Default Set</option> <option value="digitformat">Digit format</option> <option value="DiscoveryCreditCard">Discovery Credit Card</option> <option value="emailaddress">Email Address</option> <option value="firstname">First Name</option> <option value="floatrange">Float Range</option> <option value="guid">Guid</option> <option value="hexadecimalcode">Hexa Decimal Code</option> <option value="hexcolors">Hex Colors</option> <option value="IncrementalUniqueValues">Incremental Unique Values</option> <option value="integerrange">Integer Range</option> <option value="imei">IMEI Number</option> <option value="ipaddress">IP Address</option> <option value="lastname">Last Name</option> <option value="location">Location</option> <option value="macaddress">MAC Address</option> <option value="mastercardnumber">Mastercard Number</option> <option value="numberformat">Number Format</option> <option value="password">Password</option> <option value="phonenumber">Phone Number</option> <option value="phonenumberwithext">Phone Number WithExt</option> <option value="rcommand">RCommand</option> <option value="ssn">SSN</option> <option value="state">State</option> <option value="timestamp">Timestamp</option> <option value="uniquevalues">Unique Values</option> <option value="userdefinedregex">User Defined Regex Pattern</option> <option value="visacreditcardnumber">VisaCreditCard Number</option> <option value="zipcode">ZipCode</option> </select></td>'
				+ '<td> <input type="text" class="form-control noOfRows" placeholder="No of Rows" value="'+item2.percentageOfrows +'" id="noOfRows_parent'
				+ dataSetVarUpd
				+ '_child'+updatePopupCnt+'" onfocus="updateNoOfRows('
				+ dataSetVarUpd
				+ ',0,'
				+ "'out'"
				+ ')" onblur="updateNoOfRows('
				+ dataSetVarUpd
				+ ',0,'
				+ "'out'"
				+ ')"/>(<span id="noOfRowsRemaining_parent'
				+ dataSetVarUpd
				+ '_child'+updatePopupCnt+'">100</span>% Remaining)</td>'
				+ '<td><input type="text" value="'+item2.values +'" name="datavalues_parent'
				+ dataSetVarUpd
				+ '_child'+updatePopupCnt+'" id="datavalues_parent'
				+ dataSetVarUpd
				+ '_child'+updatePopupCnt+'" placeholder="Enter Values" class="datavalues form-control input-md"></td>'
				+ '<td><button type="button" onclick="popUpDeleteRow('
				+ dataSetVarUpd
				+ ',0)" class="btn btn-sm btn-danger"> <i class="glyphicon glyphicon-remove-circle"></i> </button>&nbsp;'
				+ '<button type="button" onClick="popUpAddRow('
				+ dataSetVarUpd
				+ ');" class="btn btn-sm btn-success"> <i class="glyphicon glyphicon-plus"></i> </button></td>'
				+ '</tr>';
			$('#allmodal #advancedtabCell'+dataSetVarUpd).append(modalHTML);
			//console.log("item2.dataType :"+item2.dataType);
			//console.log("item2.valuesFromSet :"+item2.valuesFromSet);
			getDataType(dataSetVarUpd, updatePopupCnt);
			getDataTypeFromValue(dataSetVarUpd, updatePopupCnt);
			$('#columnName_parent'+ dataSetVarUpd+ '_child'+updatePopupCnt).val(item2.valuesFromSet);
			$('#dataType_parent'+ dataSetVarUpd+'_child'+updatePopupCnt).val(item2.dataType);
			row_container_array.push("p" + dataSetVarUpd + "c" + updatePopupCnt);
			//changetextboxpopup(dataSetVarUpd, updatePopupCnt);
			//arrangeConstantSnoAdvanced();
			
			updatePopupCnt++;
			
			//End - Add modal multirec
		});  /*inner each loop ends*/		
		
		$('#srcClnm'+ dataSetVarUpd).val(item1.columnName);		
		$('#destClnHdr'+ dataSetVarUpd).val(item1.bucketedColumn.bucketedColumnHeader);
		
		dataSetVarUpd++;
	});
	//delete first empty row
	deleteRow(0);
}

function downloadDataSet(){
	//console.log("download json");
	getConfigDetails();
	var modifiedUrl = "rest/service/download/" + fileName
	+ ".csv";
	$.ajax({
		type : 'GET',
		url : modifiedUrl,
		success : function(data) {
			console.log('ok');
			window.location = modifiedUrl;
		},
		error : function(xhr) {
			console.log(xhr);
		}
	});
}

function transferDataSet(){
	//console.log("file transfer");
	getConfigDetails();					
	var serviceUrl = "rest/service/datagenfiletransfer";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		data : JSON.stringify($('filetransferModalNorm')
				.serializeFileTransferObject()),
				beforeSend : function() {
					jQuery("#loading").modal('show');
					$('#filetransferModalNorm').modal('hide');
					$("#wait").css("display", "block");
				},
				/*
				 * complete: function(){ $('#pageContainer').unmask(); },
				 */
				success : function(data) {
					downloadURL = data.downloadURL;
					message = data.message;
					errorCode = data.errorCode;
					if (errorCode == null || errorCode == '') {
						jQuery("#loading").modal('hide');
						$("#wait").css("display", "none");
						$("#successID").html(
								"<b>" + "File Transfer Successful : "
								+ "</b>" + message);
						jQuery("#success").modal('show');
					} else {
						jQuery("#loading").modal('hide');
						$("#wait").css("display", "none");
						// $('#pageContainer').unmask();
						$("#errorID").html(
								"<b>" + "Error in File Transfer : "
								+ "</b>" + message);
						jQuery("#error").modal('show');

					}
				},
				error : function(data) {
					//console.log(data);
					downloadURL = data.downloadURL;
					message = data.message;
					errorCode = data.errorCode;
				}
	});

	// $('#result').text(JSON.stringify($('form').serializeObject()));
	return false;
}

function saveUpdatedDataSet(){
	//console.log("saveDataSet **** ");
	getConfigDetails();	
	var serviceUrl = "rest/service/updatedataset";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		data : JSON.stringify($('form').serializeObject()),
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		/*
		 * complete: function(){ $('#pageContainer').unmask(); },
		 */
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "Data Set Configuration Saved Successfully : " + "</b>"
						+ message);
				jQuery("#success").modal('show');
			} else {
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				// $('#pageContainer').unmask();
				$("#errorID").html(
						"<b>" + "Error in Data Set Configuration Saving : " + "</b>"
						+ message);
				jQuery("#error").modal('show');

			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			// $('#pageContainer').unmask();
			$("#errorID").html(
					"<b>" + "Error in Data Set Configuration Saving : " + "</b>"
					+ message);
			jQuery("#error").modal('show');

			
		}
	});

	return false;
}

function deleteDataSet(tempFile){
	//deletedataset
	//console.log("deleteUser function");
	//console.log("tempFile :"+tempFile);
	var fileInpToDel= JSON.stringify({"fileName":tempFile});
	//var userToDel= JSON.stringify({"userInfo":{"userMeta":[{"userName":"user8"}]}});
	//console.log("fileInpToDel"+fileInpToDel);    
	getConfigDetails();	
	var serviceUrl = "rest/service/deletedataset";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		data : fileInpToDel,
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				//console.log("success");
				loadRecentList();
				$(this).closest('tr').remove();
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "Configuration Deletion Successful : "
						+ "</b>" + message);
				jQuery("#success").modal('show');
			} else {
				//console.log("errorcode part");
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				// $('#pageContainer').unmask();
				$("#errorID").html(
						"<b>" + "Error in Configuration Deletion : "
						+ "</b>" + message);
				jQuery("#error").modal('show');
			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			console.log("message: "+message);	
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			// $('#pageContainer').unmask();
			$("#errorID").html(
					"<b>" + "Error in Configuration Deletion : "
					+ "</b>" + message);
			jQuery("#error").modal('show');
		}
	});

	return false;
}

function arrangeConstantSno() {
	var i = 0;
	$('#tabCell tr').each(function() {
		$(this).find(".order").html(i);
		i++;
	});
}

function arrangeConstantSnoAdvanced() {
	var i = 0;
	$('#advancedtabCell tr').each(function() {
		$(this).find(".order").html(i);
		i++;
	});
}
/* Process string to json */
var fileName;
var host = window.location.hostname;
var portno = "";

//get Config Details
function getConfigDetails() {
	if (configparams.enabled) {
		portno = configparams.port;
	} else {
		portno = configparams.defaultport;
	}
	// console.log(portno);
}

$.fn.serializeObject = function() {
	/*function serializeObject(){*/
	//console.log("serializeObject");
	/*    var $inputs = $('#createForm :input');

    // not sure if you wanted this, but I thought I'd add it.
    // get an associative array of just the values.
    var values = {};
    var val = "";
    $inputs.each(function() {
        values[this.name] = $(this).val();
        val = val + "values[this.name]" +" : "+$(this).val();
    });
    alert("generateData set")
    alert(values);
    alert("VAL : " +val);
    return values;*/
	var o = {};
	o.fileName = $("#jobName").val();
	o.fileType = $("#fileType").val();
	fileName = o.fileName;
	var dataSetLength = $('#tabCell tr').length;
	o.fileInfo = {};
	o.fileInfo.listOfColumns = [];
	var parentIds = getParentIds();
	for (var i = 0; i < parentIds.length; i++) {
		var id = parentIds[i];
		var ColumnMeta = new Object();
		ColumnMeta.columnName = $("#addr" + id + " .colTitle").val();
		ColumnMeta.type = "";
		ColumnMeta.valueType = "";
		ColumnMeta.dataType = $("#addr" + id + " .dataType").val();
		ColumnMeta.values = $("#addr" + id + " .datavalues").val();
		ColumnMeta.order = $("#addr" + id + " .order").val();
		if (ColumnMeta.columnName.length == 0) {
			ColumnMeta.columnName = null;
		}
		if (ColumnMeta.values.length == 0) {
			ColumnMeta.values = null;
		}
		ColumnMeta.bucketedColumn = null;
		var BucketedColumn = new Object();
		BucketedColumn.bucketedColumnHeader = $("#destClnHdr" + id).val();
		BucketedColumn.columnMapper = [];
		var temp_row_container_array = row_container_array.slice(0);
		for (var j = 0; j <= row_container_array.length; j++) {
			var index = searchStringInArray("p" + id + "c",
					temp_row_container_array);
			if (index > -1) {
				var advId = temp_row_container_array[index].split("c")[1]
				.substr(0);
				temp_row_container_array.splice(index, 1);
				var mapper = new Object();
				mapper.valuesFromSet = $(
						"#columnName_parent" + id + "_child" + advId).val();
				mapper.type = "";
				mapper.valueType = "";
				mapper.dataType = $(
						"#dataType_parent" + id + "_child" + advId).val();
				mapper.percentageOfrows = $(
						"#popupadd_row" + id + "_advancedaddr" + advId
						+ " .noOfRows").val();
				mapper.values = $(
						"#popupadd_row" + id + "_advancedaddr" + advId
						+ " .datavalues").val();
				BucketedColumn.columnMapper[advId] = mapper;
			}

		}
		ColumnMeta.bucketedColumn = BucketedColumn;
		o.fileInfo.listOfColumns[i] = ColumnMeta;
	}
	o.numberOfRows = $("#numRows").val();
	return o;
};


var downloadURL;
var message;
var errorCode;

$(function() {
	$('form').submit(
			function() {
				//alert("Inside Form submit");
				//console.log("form submit");
				/*
				 * jQuery("#loading").modal('show'); $("#wait").css("display",
				 * "block");
				 */
				getConfigDetails();				
				var serviceUrl = "rest/service/dataprocessservice";
				$.ajax({
					type : 'POST',
					contentType : 'application/json',
					url : serviceUrl,
					dataType : "json",
					data : JSON.stringify($('form').serializeObject()),
					beforeSend : function() {
						jQuery("#loading").modal('show');
						$("#wait").css("display", "block");
					},
					/*
					 * complete: function(){ $('#pageContainer').unmask(); },
					 */
					success : function(data) {
						downloadURL = data.downloadURL;
						message = data.message;
						errorCode = data.errorCode;
						if (errorCode == null || errorCode == '') {
							jQuery("#loading").modal('hide');
							$("#wait").css("display", "none");
							$("#successID").html(
									"<b>" + "Data Generation Successful : "
									+ "</b>" + message);
							jQuery("#success").modal('show');
						} else {
							jQuery("#loading").modal('hide');
							$("#wait").css("display", "none");
							// $('#pageContainer').unmask();
							$("#errorID").html(
									"<b>" + "Error in Data Generation : "
									+ "</b>" + message);
							jQuery("#error").modal('show');

						}
					},
					error : function(data) {
						// console.log(data);
						downloadURL = data.downloadURL;
						message = data.message;
						errorCode = data.errorCode;
					}
				});

				// $('#result').text(JSON.stringify($('form').serializeObject()));
				return false;
			});
});
function generateDataSet() {
	/*
	 * $('form').submit( function() {
	 */
	//console.log("form submit onclick fn11");
	/* alert("Form.ser" + $('form').serializeObject()); */
	/*
	 * jQuery("#loading").modal('show'); $("#wait").css("display", "block");
	 */
	//mandatory check
	getConfigDetails();	
	var serviceUrl = "rest/service/dataprocessservice";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		/* data : JSON.stringify($('form').serializeObject()), */
		data : JSON.stringify($('form').serializeObject()),
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		/*
		 * complete: function(){ $('#pageContainer').unmask(); },
		 */
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "Data Generation Successful : " + "</b>"
						+ message);
				jQuery("#success").modal('show');
			} else {
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				// $('#pageContainer').unmask();
				$("#errorID").html(
						"<b>" + "Error in Data Generation : " + "</b>"
						+ message);
				jQuery("#error").modal('show');

			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			// $('#pageContainer').unmask();
			$("#errorID").html(
					"<b>" + "Error in Data Generation : " + "</b>"
					+ message);
			jQuery("#error").modal('show');


		}
	});

	// $('#result').text(JSON.stringify($('form').serializeObject()));
	return false;
	/* }); */

}

/* Download */
$(function() {
	$('#downloadJSON').on(
			'click',
			function(e) {
				//console.log("download json");
				getConfigDetails();				
				var modifiedUrl = "rest/service/download/" + fileName
				+ ".csv";
				$.ajax({
					type : 'GET',
					url : modifiedUrl,
					success : function(data) {
						console.log('ok');
						window.location = modifiedUrl;
					},
					error : function(xhr) {
						console.log(xhr);
					}
				});
			});

});

$.fn.serializeFileTransferObject = function() {
	//console.log("serializeObject transfer");
	var o = {};
	o.defaultFileTransferType = "upload";
	o.fileName = fileName;
	o.host = $("#hostClmHdr").val();
	o.userId = $("#useridClmHdr").val();
	o.password = $("#passwordClmHdr").val();
	o.remoteDirectory = $("#remotedirectoryClmHdr").val();
	o.localDirectory = "";
	o.fileTransferType = $("#fileTransferClm").val();
	return o;
};

$(function() {
	$('#filetransfer').on(
			'click',
			function(e) {
				//console.log("fiel transfer");
				getConfigDetails();				
				var serviceUrl = "rest/service/datagenfiletransfer";
				$.ajax({
					type : 'POST',
					contentType : 'application/json',
					url : serviceUrl,
					dataType : "json",
					data : JSON.stringify($('filetransferModalNorm')
							.serializeFileTransferObject()),
							beforeSend : function() {
								jQuery("#loading").modal('show');
								$('#filetransferModalNorm').modal('hide');
								$("#wait").css("display", "block");
							},
							/*
							 * complete: function(){ $('#pageContainer').unmask(); },
							 */
							success : function(data) {
								downloadURL = data.downloadURL;
								message = data.message;
								errorCode = data.errorCode;
								if (errorCode == null || errorCode == '') {
									jQuery("#loading").modal('hide');
									$("#wait").css("display", "none");
									$("#successID").html(
											"<b>" + "File Transfer Successful : "
											+ "</b>" + message);
									jQuery("#success").modal('show');
								} else {
									jQuery("#loading").modal('hide');
									$("#wait").css("display", "none");
									// $('#pageContainer').unmask();
									$("#errorID").html(
											"<b>" + "Error in File Transfer : "
											+ "</b>" + message);
									jQuery("#error").modal('show');

								}
							},
							error : function(data) {
								// console.log(data);
								downloadURL = data.downloadURL;
								message = data.message;
								errorCode = data.errorCode;
							}
				});

				// $('#result').text(JSON.stringify($('form').serializeObject()));
				return false;

			});

});

//add row in main page
//var dataSetVar = 1;

function addRow() {
	//console.log("addRowfn");
	var lastMainTrID = $('#tabCell tr:last').attr('id');
	//console.log("lastMainTrID >"+lastMainTrID);
	if (lastMainTrID != null) {
		var matches = lastMainTrID.match(/\d+$/);
		var dataSetVar = parseInt(matches[0]);
	} else {
		var dataSetVar = 0;
	}
	//console.log("dataSetVar in addRow beginning >"+dataSetVar);
	$('#addr' + dataSetVar)
	.html(
			"<th scope='row' id='order' class='order' name='dataSetOrder'>"
			+ (dataSetVar + 1)
			+ "</th>"
			+ "<td><input name='columnName"
			+ dataSetVar
			+ "' id='columnName"
			+ dataSetVar
			+ "' onChange='updateSourceHeader("
			+ dataSetVar
			+ ")' type='text' placeholder='Column Name' class='colTitle form-control input-md' /> </td>"
			+ "<td><select name='dataType"
			+ dataSetVar
			+ "' id='dataType"
			+ dataSetVar
			+ "' class='dataType' onChange='getDataType("
			+ dataSetVar
			+ ",0);'> <option value='alphanumeric'>Alpha Numeric</option> <option value='americancard'>American Card</option> <option value='city'>City</option> <option value='country'>Country</option> <option value='cvv'>CVV</option> <option value='date'>Date</option> <option value='defaultset'>Default Set</option> <option value='digitformat'>Digit format</option> <option value='DiscoveryCreditCard'>Discovery Credit Card</option> <option value='emailaddress'>Email Address</option> <option value='firstname'>First Name</option> <option value='floatrange'>Float Range</option> <option value='guid'>Guid</option> <option value='hexadecimalcode'>Hexa Decimal Code</option> <option value='hexcolors'>Hex Colors</option> <option value='IncrementalUniqueValues'>Incremental Unique Values</option> <option value='integerrange'>Integer Range</option> <option value='imei'>IMEI Number</option> <option value='ipaddress'>IP Address</option> <option value='lastname'>Last Name</option> <option value='location'>Location</option> <option value='macaddress'>MAC Address</option> <option value='mastercardnumber'>Mastercard Number</option> <option value='numberformat'>Number Format</option> <option value='password'>Password</option> <option value='phonenumber'>Phone Number</option> <option value='phonenumberwithext'>Phone Number WithExt</option> <option value='rcommand'>RCommand</option> <option value='ssn'>SSN</option> <option value='state'>State</option> <option value='timestamp'>Timestamp</option> <option value='uniquevalues'>Unique Values</option> <option value='userdefinedregex'>User Defined Regex Pattern</option> <option value='visacreditcardnumber'>VisaCreditCard Number</option> <option value='zipcode'>ZipCode</option> </select></td>"
			+ "<td><input type='text' name='datavalues"
			+ dataSetVar
			+ "' id='datavalues"
			+ dataSetVar
			+ "' placeholder='Enter Values' class='datavalues form-control input-md' onchange='getDataTypeFromValue("
			+ dataSetVar
			+ ",0)'></td>"
			+ "<td><span id='addrAdvBtn"
			+ dataSetVar
			+ "' data-toggle='modal' data-target='#addr"
			+ dataSetVar
			+ "myModalNorm' class='glyphicon glyphicon-new-window popupBtn'  aria-hidden='true'></span> "
			+ "<!--span class='glyphicon glyphicon-pushpin bucketingIcon' show='bucketing'></span>-->"
			+ "</td>"
			+ "<td><button type='button'  onclick='deleteRow("
			+ dataSetVar
			+ ")' class='btn btn-sm btn-danger'> <i class='glyphicon glyphicon-remove-circle'></i> </button>&nbsp;"
			+ "<button type='button'  onclick='addRow()' class='btn btn-sm btn-success'> <i class='glyphicon glyphicon-plus'></i> </button></td>"

	);

	$('#tabCell').append('<tr id="addr' + (dataSetVar + 1) + '"></tr>');
	var appendModal = '<div class="modal fade in" id="addr'
		+ dataSetVar
		+ 'myModalNorm" tabindex="-1" role="dialog"'
		+ 'aria-labelledby="myModalLabel" aria-hidden="true">'
		+ '<div class="modal-dialog modal-lg">'
		+ '<div class="modal-content">'
		+ '<div class="modal-header">'
		+ '<button type="button" class="close"'
		+ 'data-dismiss="modal">'
		+ '<span aria-hidden="true">&times;</span>'
		+ '<span class="sr-only">Close</span>'
		+ '</button>'
		+ '<h5 class="modal-title" id="myModalLabel"><b>Segment - Column Mapper</b></h5>'
		+ '</div>'
		+ '<div class="modal-body">'
		+ '<table>'
		+ '<div class="firstDiv">'
		+ '<td><div class="srcClnm"><label>Select Source Column &nbsp;&nbsp;&nbsp;</label> </td><td><input type="text" id="srcClnm'
		+ dataSetVar
		+ '" onchange="updateHeader('
		+ dataSetVar
		+ ')"class="form-control sourceClnHdrInp" placeholder="Source Column Header"</div></td>'
		+ '<td><div class="destClnHdr"> <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Destination Column Header &nbsp;&nbsp;&nbsp;</label></td><td><input type="text" id="destClnHdr'
		+ dataSetVar
		+ '" class="form-control destClnHdrInp" placeholder="Destination Column Header"/> </div> </td>'
		+ '</div>'
		+ '</table>'
		+ '<div class="tableDiv">'
		+ '<table class="table table-bordered table-striped" id="advancedtabCell'
		+ dataSetVar
		+ '">'
		+ '<thead>'
		+ '<br/>'
		+ '<tr>'
		+ '<th> <label>Values From Set</label> </th>'
		+ '<th> <label>Data Type</label>  </th>'
		+ '<th width="5%"> <label>Rows(Percentage)<input type="hidden" id="noOfRows'
		+ dataSetVar
		+ '" value="100" ></label> </th>'
		+ '<th> <label>Values</label> </th>'
		+ '<th><button type="button" id="popupadd_row'
		+ dataSetVar
		+ '"'
		+ 'class="btn btn-sm btn-success" onClick="popUpAddRow('
		+ dataSetVar
		+ ');">'
		+ '<i class="glyphicon glyphicon-plus"></i>Add Row'
		+ '</button></th>'
		+ '</tr>'
		+ '</thead>'
		+ '<tbody>'
		+ '<tr id="popupadd_row'
		+ dataSetVar
		+ '_advancedaddr0" class="trow">'
		+ '<td><select style="height: 33px; width: 130px;" name="columnName_parent'
		+ dataSetVar
		+ '_child0" id="columnName_parent'
		+ dataSetVar
		+ '_child0"><select></td>'
		+ '<td><select name="dataType_parent'
		+ dataSetVar
		+ '_child0" id="dataType_parent'
		+ dataSetVar
		+ '_child0" class="dataType" onChange="changetextboxpopup('
		+ dataSetVar
		+ ',0);"> <option value="alphanumeric">Alpha Numeric</option> <option value="americancard">American Card</option> <option value="city">City</option> <option value="country">Country</option> <option value="cvv">CVV</option> <option value="date">Date</option> <option value="defaultset">Default Set</option> <option value="digitformat">Digit format</option> <option value="DiscoveryCreditCard">Discovery Credit Card</option> <option value="emailaddress">Email Address</option> <option value="firstname">First Name</option> <option value="floatrange">Float Range</option> <option value="guid">Guid</option> <option value="hexadecimalcode">Hexa Decimal Code</option> <option value="hexcolors">Hex Colors</option> <option value="IncrementalUniqueValues">Incremental Unique Values</option> <option value="integerrange">Integer Range</option> <option value="imei">IMEI Number</option> <option value="ipaddress">IP Address</option> <option value="lastname">Last Name</option> <option value="location">Location</option> <option value="macaddress">MAC Address</option> <option value="mastercardnumber">Mastercard Number</option> <option value="numberformat">Number Format</option> <option value="password">Password</option> <option value="phonenumber">Phone Number</option> <option value="phonenumberwithext">Phone Number WithExt</option> <option value="rcommand">RCommand</option> <option value="ssn">SSN</option> <option value="state">State</option> <option value="timestamp">Timestamp</option> <option value="uniquevalues">Unique Values</option> <option value="userdefinedregex">User Defined Regex Pattern</option> <option value="visacreditcardnumber">VisaCreditCard Number</option> <option value="zipcode">ZipCode</option> </select></td>'
		+ '<td> <input type="text" class="form-control noOfRows" placeholder="No of Rows" id="noOfRows_parent'
		+ dataSetVar
		+ '_child0" onfocus="updateNoOfRows('
		+ dataSetVar
		+ ',0,'
		+ "'out'"
		+ ')" onblur="updateNoOfRows('
		+ dataSetVar
		+ ',0,'
		+ "'out'"
		+ ')"/>(<span id="noOfRowsRemaining_parent'
		+ dataSetVar
		+ '_child0">100</span>% Remaining)</td>'
		+ '<td><input type="text" name="datavalues_parent'
		+ dataSetVar
		+ '_child0" id="datavalues_parent'
		+ dataSetVar
		+ '_child0" placeholder="Enter Values" class="datavalues form-control input-md"></td>'
		+ '<td><button type="button" onclick="popUpDeleteRow('
		+ dataSetVar
		+ ',0)" class="btn btn-sm btn-danger"> <i class="glyphicon glyphicon-remove-circle"></i> </button>&nbsp;'
		+ '<button type="button" onClick="popUpAddRow('
		+ dataSetVar
		+ ');" class="btn btn-sm btn-success"> <i class="glyphicon glyphicon-plus"></i> </button></td>'
		+ '</tr>'
		+

		'</tbody>'
		+ '</table>'
		+ '</div>'
		+ '</div>'
		+ '<div class="modal-footer">'
		+ '<button type="button" class="btn btn-primary" data-dismiss="modal">'
		+ 'Save' + '</button>' +

		'</div>' + '</div>' + '</div>' + '</div>';
	$('#allmodal').append(appendModal);
	// defult call getDataType
	getDataType(dataSetVar, 0);

	// Add to Row Container array
	row_container_array.push("p" + dataSetVar + "c0");

	// appendModal.attr('id', 'add'+dataSetVar+'myModalNorm');
	dataSetVar++;
	arrangeConstantSno();
	arrangeConstantSnoAdvanced();

}

//add row in pop up
function popUpAddRow(id) {

	var lastTrID = $('#advancedtabCell' + id + ' tr:last').attr('id');
	if (lastTrID != null) {
		var matches = lastTrID.match(/\d+$/);
		var popUpdataSetVar = parseInt(matches[0]) + 1;
	} else {
		var popUpdataSetVar = 0;
	}
	$('#advancedtabCell' + id + ' tr:last')
	.after(
			"<tr id='popupadd_row"
			+ id
			+ "_advancedaddr"
			+ popUpdataSetVar
			+ "'><td><select style='height: 33px; width: 130px;' name='columnName_parent"
			+ id
			+ "_child"
			+ popUpdataSetVar
			+ "' id='columnName_parent"
			+ id
			+ "_child"
			+ popUpdataSetVar
			+ "'><select></td>"
			+ "<td><select style='height: 33px; border-radius: 4px; padding: 5px; float: left;' name='dataType' id='dataType_parent"
			+ id
			+ "_child"
			+ popUpdataSetVar
			+ "' class='dataType_parent"
			+ id
			+ "_child"
			+ popUpdataSetVar
			+ "' onChange='changetextboxpopup("
			+ id
			+ ","
			+ popUpdataSetVar
			+ ");'> <option value='alphanumeric'>Alpha Numeric</option> <option value='americancard'>American Card</option> <option value='city'>City</option> <option value='country'>Country</option> <option value='cvv'>CVV</option> <option value='date'>Date</option> <option value='defaultset'>Default Set</option> <option value='digitformat'>Digit format</option> <option value='DiscoveryCreditCard'>Discovery Credit Card</option> <option value='emailaddress'>Email Address</option> <option value='firstname'>First Name</option> <option value='floatrange'>Float Range</option> <option value='guid'>Guid</option> <option value='hexadecimalcode'>Hexa Decimal Code</option> <option value='hexcolors'>Hex Colors</option> <option value='IncrementalUniqueValues'>Incremental Unique Values</option> <option value='integerrange'>Integer Range</option> <option value='imei'>IMEI Number</option> <option value='ipaddress'>IP Address</option> <option value='lastname'>Last Name</option> <option value='location'>Location</option> <option value='macaddress'>MAC Address</option> <option value='mastercardnumber'>Mastercard Number</option> <option value='numberformat'>Number Format</option> <option value='password'>Password</option> <option value='phonenumber'>Phone Number</option> <option value='phonenumberwithext'>Phone Number WithExt</option> <option value='rcommand'>RCommand</option> <option value='ssn'>SSN</option> <option value='state'>State</option> <option value='timestamp'>Timestamp</option> <option value='uniquevalues'>Unique Values</option> <option value='userdefinedregex'>User Defined Regex Pattern</option> <option value='visacreditcardnumber'>VisaCreditCard Number</option> <option value='zipcode'>ZipCode</option> </select></td>"
			+ '<td> <input type="text" class="form-control noOfRows" placeholder="No of Rows" id="noOfRows_parent'
			+ id
			+ '_child'
			+ popUpdataSetVar
			+ '" onfocus="updateNoOfRows('
			+ id
			+ ','
			+ popUpdataSetVar
			+ ','
			+ "'in'"
			+ ')" onblur="updateNoOfRows('
			+ id
			+ ','
			+ popUpdataSetVar
			+ ','
			+ "'out'"
			+ ')"/>(<span id="noOfRowsRemaining_parent'
			+ id
			+ '_child'
			+ popUpdataSetVar
			+ '"></span>% Remaining)</td>'
			+ "<td><input type='text' name='datavalues_parent"
			+ id
			+ "_child"
			+ popUpdataSetVar
			+ "' id='datavalues_parent"
			+ id
			+ "_child"
			+ popUpdataSetVar
			+ "' placeholder='Enter Values' class='datavalues form-control input-md'></td>"
			+ "<td><button type='button' onclick='popUpDeleteRow("
			+ id
			+ ","
			+ popUpdataSetVar
			+ ")' class='btn btn-sm btn-danger'> <i class='glyphicon glyphicon-remove-circle'></i> </button>&nbsp;"
			+ "<button type='button' onclick='popUpAddRow("
			+ id
			+ ")' class='btn btn-sm btn-success'> <i class='glyphicon glyphicon-plus'></i> </button></td></tr>");
	// Add to Row Container array
	row_container_array.push("p" + id + "c" + popUpdataSetVar);

	getDataType(id, popUpdataSetVar);
	changetextboxpopup(id, popUpdataSetVar);
	popUpdataSetVar++;
	arrangeConstantSnoAdvanced();

}

//delete row from main page
function deleteRow(id) {
	var length = row_container_array.length;
	for (var i = 0; i < length; i++) {
		var index = searchStringInArray("p" + id + "c", row_container_array);
		removeElementFromArray(index);
	}
	$('#addr' + id).remove();
	arrangeConstantSno();
}

//delete row from pop up
function popUpDeleteRow(id, advId) {
	var noOfRows = parseFloat($('#noOfRows_parent' + id + '_child' + advId)
			.val() || 0);
	$('#noOfRows' + id).val(parseFloat($('#noOfRows' + id).val()) + noOfRows);
	$('#popupadd_row' + id + '_advancedaddr' + advId).remove();
	var index = searchStringInArray("p" + id + "c" + advId, row_container_array);
	removeElementFromArray(index);
	arrangeConstantSnoAdvanced();
}

//update Source column header(Child)
function updateSourceHeader(id) {
	$('#srcClnm' + id).val($('#columnName' + id).val());
}

//update column header (Parent)
function updateHeader(id) {
	$('#columnName' + id).val($('#srcClnm' + id).val());
}

//update No Of Rows
function updateNoOfRows(id, advId, status) {
	// alert(id+advId);
	var advId = parseInt(advId);
	// check filed contains some value

	var rows = $('#noOfRows_parent' + id + '_child' + advId).val() || 0;
	// alert(rows+status);
	var total = parseFloat($('#noOfRows' + id).val());
	// console.log("Total1 : "+total);
	if (status == "in" && rows > 0) {
		// alert("enter");
		total = total + parseFloat(rows);
		$('#noOfRows' + id).val(total);
		// alert(parseFloat($('#noOfRows'+id).val()));
	} else {
		var rows_remain = total - parseFloat(rows);
		if (rows_remain >= 0) {
			$('#noOfRowsRemaining_parent' + id + '_child' + advId).html(
					rows_remain);
			$('#noOfRows' + id).val(rows_remain);
		} else {
			$('#noOfRows_parent' + id + '_child' + advId).val("");
			$('#noOfRowsRemaining_parent' + id + '_child' + advId).html("");
			alert("Total percentage can not be more than 100.");
		}
	}
}

//JSON Read
function getDataType(id, advId) {
	//console.log(" in getDataType");
	var key = "addr";
	var elementid = id;
	var datatypevalue = $("#addr" + elementid + " .dataType").val();
	//console.log("datatypevalue :" + datatypevalue);
	if ((datatypevalue === "americancard") || (datatypevalue === "city")
			|| (datatypevalue === "country") || (datatypevalue === "cvv")
			|| (datatypevalue === "DiscoveryCreditCard")
			|| (datatypevalue === "emailaddress")
			|| (datatypevalue === "firstname") || (datatypevalue === "guid")
			|| (datatypevalue === "hexadecimalcode")
			|| (datatypevalue === "hexcolors") || (datatypevalue === "imei")
			|| (datatypevalue === "ipaddress")
			|| (datatypevalue === "lastname") || (datatypevalue === "location")
			|| (datatypevalue === "macaddress")
			|| (datatypevalue === "mastercardnumber")
			|| (datatypevalue === "password")
			|| (datatypevalue === "phonenumber")
			|| (datatypevalue === "phonenumberwithext")
			|| (datatypevalue === "ssn") || (datatypevalue === "state")
			|| (datatypevalue === "visacreditcardnumber")
			|| (datatypevalue === "zipcode")) {
		//console.log("Inside If");
		$("#addr" + elementid + " .datavalues").val('');
		$("#addr" + elementid + " .datavalues").prop("disabled", true);
	} else {
		//console.log("Else part");
		$("#addr" + elementid + " .datavalues").prop("disabled", false);
	}

	var item = datatypevalue;
	//console.log("item :" + item);
	//console.log("datavalues id :" + id);
	if (item != "Select") {
		var tooltipText = MouseHoverText(item);
		//console.log("item :" + item);
		//console.log("tooltipText :" + tooltipText);
		//console.log("datavalues id :" + id);
/*		if ( item !="alphanumeric") {
			$("#datavalues" + id).tooltip('hide').attr('data-original-title',
					tooltipText).tooltip('fixTitle').tooltip('show');

		}*/
	}

	var type = $('#dataType' + id).val();
	//console.log("type :" + type);
	if ((type == "alphanumeric") || (type == "americancard")
			|| (type === "cvv") || (type === "date")
			|| (type === "digitformat") || (type == "DiscoveryCreditCard")
			|| (type == "floatrange") || (type == "guid")
			|| (type == "hexadecimalcode") || (type == "hexcolors")
			|| (type == "IncrementalUniqueValues") || (type == "integerrange")
			|| (type == "imei") || (type == "ipaddress")
			|| (type == "macaddress") || (type == "mastercardnumber")
			|| (type == "numberformat") || (type == "password")
			|| (type == "phonenumber") || (type == "phonenumberwithext")
			|| (type == "rcommand") || (type == "ssn") || (type == "timestamp")
			|| (type == "uniquevalues") || (type == "userdefinedregex")
			|| (type == "visacreditcardnumber")) {
		//console.log("if cond datatoggle");
		$('#addrAdvBtn' + id).attr('data-toggle', '');
		$('#addrAdvBtn' + id).css('color', '#808080');
		return;
	}
	if (type == "defaultset") {
		//console.log("type defaultset if :" + type);
		getDataTypeFromValue(id, advId);
		return;
	}
	$('#addrAdvBtn' + id).attr('data-toggle', 'modal');
	$('#addrAdvBtn' + id).css('color', '#0A84C8');

	// call ajax only one time
	//console.log("ajax_count" + ajax_count);
	if (ajax_count < 1) {
		json = (function() {
			var json = null;
			getConfigDetails();			
			var serviceUrl = "rest/service/dbdataretrieval";

			$.ajax({
				'async' : false,
				'global' : false,
				'url' : serviceUrl,
				'dataType' : "json",
				'success' : function(data) {
					json = data;
					// console.log(json);
				}
			});

			return json;
		})();
		ajax_count++;
	}
	$('#columnName_parent' + id + '_child' + advId).find('option').remove()
	.end();

	var type_array = json["dbDatadetailsList"][type];
	jQuery.unique(type_array);
	for (var i = 0; i < type_array.length; i++) {
		$('#columnName_parent' + id + '_child' + advId).append($('<option>', {
			value : type_array[i],
			text : type_array[i]
		}));
	}

}

//special function for default set
function getDataTypeFromValue(id, advId) {
	var type = $('#dataType' + id).val();
	var str = $('#datavalues' + id).val();
	if (type == "defaultset") {
		var arrayStr = str.split(",");
		$('#addrAdvBtn' + id).attr('data-toggle', 'modal');
		$('#addrAdvBtn' + id).css('color', '#0A84C8');
		$('#columnName_parent' + id + '_child' + advId).find('option').remove()
		.end();
		for (var i = 0; i < arrayStr.length; i++) {
			$('#columnName_parent' + id + '_child' + advId).append(
					$('<option>', {
						value : arrayStr[i],
						text : arrayStr[i]
					}));
		}
	}
}

var MouseHoverText = function(id) {
	switch (id) {
	case "alphanumeric":
		return "Description : Generate Alphanumeric codes based on the parameters in values field. Usage : Select drop down Alpha Numeric and set the values field in the format <number Of Digits,number Of Alphabets> Example : alphanumeric(3,2) generates 459db ";
		break;
	case "americancard":
		return "Description : Generate Americancard numbers automatically  ";
		break;
	case "city":
		return "Description : Generate City automatically  ";
		break;
	case "country":
		return "Description : Generate Country automatically  ";
		break;
	case "cvv":
		return "Description : Generate CVV numbers automatically  ";
		break;
	case "date":
		return "Description : Generate random dates between start date and the end date based on the parameters in values field. Usage : Select drop down Date and and set the values field in the format <start date in dd/MM/yyyy>-<end date in dd/MM/yyyy>#<output date format> Example : 01/01/2014-01/01/2016#dd/MMM/yyyy will generate dates between 01/01/2014 and 01/01/2016 in dd/MMM/yyyy format";
		break;
	case "defaultset":
		return "Description : Generate user defined values from the set provided in the values field separated by comma. Usage : Select drop down Default Set and set the values field in the format <attributes separated by comma> Example : Machine1,Machine2,Machine3 will generate values from any of the Machine1 or Machine2 or Machine3";
		break;
	case "digitformat":
		return "Description : Generate random digits based on the parameters in values field.  Usage : Select drop down Digit Format and set the values field in the format <n digit number> Example : 5 in values field will generate numbers like 45689";
		break;
	case "DiscoveryCreditCard":
		return "Description : Generate DiscoveryCreditCard numbers automatically  ";
		break;
	case "emailaddress":
		return "Description : Generate emailaddress automatically  ";
		break;
	case "firstname":
		return "Description : Generate firstname automatically  ";
		break;
	case "floatrange":
		return "Description : Generate random float values between the given ranges. Usage : <lower range> to <upper range> Example : 1 to 10000 will generate float range between 1 and 10000";
		break;
	case "guid":
		return "Description : Generate guid automatically  ";
		break;
	case "hexadecimalcode":
		return "Description : Generate hexadecimalcode automatically  ";
		break;
	case "hexcolors":
		return "Description : Generate hexcolors automatically  ";
		break;
	case "IncrementalUniqueValues":
		return "Description : Generate IncrementalUniqueValues between the range .Specify the start and end value to generate the unique key and the value by which you want to increment the values. Usage : <lower range>to<Upper Range>,<increment sequence> Example : 10 to 100,2 will generate the values between 10 and 100 increment by 2";
		break;
	case "integerrange":
		return "Description : Generate random integer values between the given ranges. Example: -100 to 100 ";
		break;
	case "imei":
		return "Description : Generate imei automatically  ";
		break;
	case "ipaddress":
		return "Description : Generate ipaddress automatically  ";
		break;
	case "lastname":
		return "Description : Generate lastname automatically  ";
		break;
	case "location":
		return "Description : Generate location automatically  ";
		break;
	case "macaddress":
		return "Description : Generate macaddress automatically  ";
		break;
	case "mastercardnumber":
		return "Description : Generate mastercardnumber automatically  ";
		break;
	case "numberformat":
		return "Description : Generate numbers n,m with length m and precision of m.Example : 4,2 will generate numbers like 45.23  ";
		break;
	case "password":
		return "Description : Generate password automatically  ";
		break;
	case "phonenumber":
		return "Description : Generate phonenumber automatically  ";
		break;
	case "phonenumberwithext":
		return "Description : Generate phonenumberwithext automatically  ";
		break;
	case "rcommand":
		return "Description : Execute the rcommand specified.The value of n should be equal to the total number of rows. Example rnorm(n=1000,mean=100,sd=5.2) ";
		break;
	case "ssn":
		return "Description : Generate ssn automatically  ";
		break;
	case "state":
		return "Description : Generate state automatically  ";
		break;
	case "timestamp":
		return "Description : Generate the timestamp.Enter the fromDate-ToDate to generate standard Timestamp. Example : 01/01/2014-01/01/2016";
		break;
	case "uniquevalues":
		return "Description : Generate uniquevalues.Specify the start value and end value to generate the unique key.Example :1 to 1000  ";
		break;
	case "userdefinedregex":
		return "Description : Generate user defined regex pattern. Example : [0-9]{2} ";
		break;
	case "visacreditcardnumber":
		return "Description : Generate visacreditcardnumber automatically  ";
		break;
	case "zipcode":
		return "Description : Generate zipcode automatically  ";
		break;
	default:
		return "Data Type : " + id + " Usage : ";
	}
};

/* logout */
function logout() {
	//alert("Logout");
	getConfigDetails();				
	var modifiedUrl = "rest/service/logout" ;						
	$.ajax({
		type : 'GET',
		url : modifiedUrl,
		success : function(data) {
			console.log('ok');
			window.location = modifiedUrl;
			//$(window).attr("location","login.html");
			window.location.href = "login.html";
		},
		error : function(xhr) {
			console.log(xhr);
		}
	});
}
/* Save DataSet*/
function saveDataSet() {
	//console.log("saveDataSet **** ");
	getConfigDetails();	
	var serviceUrl = "rest/service/savedataset";
	$.ajax({
		type : 'POST',
		contentType : 'application/json',
		url : serviceUrl,
		dataType : "json",
		data : JSON.stringify($('form').serializeObject()),
		beforeSend : function() {
			jQuery("#loading").modal('show');
			$("#wait").css("display", "block");
		},
		/*
		 * complete: function(){ $('#pageContainer').unmask(); },
		 */
		success : function(data) {
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			if (errorCode == null || errorCode == '') {
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				$("#successID").html(
						"<b>" + "Data Set Configuration Successfully Saved : " + "</b>"
						+ message);
				jQuery("#success").modal('show');
			} else {
				jQuery("#loading").modal('hide');
				$("#wait").css("display", "none");
				// $('#pageContainer').unmask();
				$("#errorID").html(
						"<b>" + "Error in Data Set Configuration Saving : " + "</b>"
						+ message);
				jQuery("#error").modal('show');

			}
		},
		error : function(data) {
			//console.log(data);
			downloadURL = data.downloadURL;
			message = data.message;
			errorCode = data.errorCode;
			jQuery("#loading").modal('hide');
			$("#wait").css("display", "none");
			// $('#pageContainer').unmask();
			$("#errorID").html(
					"<b>" + "Error in Data Set Configuration Saving : " + "</b>"
					+ message);
			jQuery("#error").modal('show');

			
		}
	});

	// $('#result').text(JSON.stringify($('form').serializeObject()));
	return false;
	/* }); */
}
function changetextboxpopup(elementid, childelementid) {

	var key = "popupadd_row";
	var datatypevalue = $(
			"#dataType_parent" + elementid + "_child" + childelementid).val();

	if ((datatypevalue === "americancard") || (datatypevalue === "city")
			|| (datatypevalue === "country") || (datatypevalue === "cvv")
			|| (datatypevalue === "DiscoveryCreditCard")
			|| (datatypevalue === "emailaddress")
			|| (datatypevalue === "firstname") || (datatypevalue === "guid")
			|| (datatypevalue === "hexadecimalcode")
			|| (datatypevalue === "hexcolors") || (datatypevalue === "imei")
			|| (datatypevalue === "ipaddress")
			|| (datatypevalue === "lastname") || (datatypevalue === "location")
			|| (datatypevalue === "macaddress")
			|| (datatypevalue === "mastercardnumber")
			|| (datatypevalue === "password")
			|| (datatypevalue === "phonenumber")
			|| (datatypevalue === "phonenumberwithext")
			|| (datatypevalue === "ssn") || (datatypevalue === "state")
			|| (datatypevalue === "visacreditcardnumber")
			|| (datatypevalue === "zipcode")) {
		$("#datavalues_parent" + elementid + "_child" + childelementid).val('');
		$("#datavalues_parent" + elementid + "_child" + childelementid).prop(
				"disabled", true);
	} else {
		$("#datavalues_parent" + elementid + "_child" + childelementid).prop(
				"disabled", false);
	}

	var item = datatypevalue;
	if (item !== "Select") {
		var tooltipText = MouseHoverText(item);
		$("#datavalues_parent" + elementid + "_child" + childelementid)
		.tooltip('hide').attr('data-original-title', tooltipText)
		.tooltip('fixTitle').tooltip('show');
	}
}
