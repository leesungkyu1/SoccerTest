
var userInfoData;
var teamInfoData;

function goDetailView(channelIdx, channelPlayIdx){
	console.log(channelIdx, channelPlayIdx);
		location.href = '/channel/play/'+channelIdx+"/"+channelPlayIdx;
}

//const test1 = document.querySelector('#test1');
//test1.addEventListener('click', function goDetailView(channelIdx, channelPlayIdx){
//	console.log('클릭했음');
//
//})
function playResultMove(channelPlayIdx){
	location.href='/channel/play/goal/'+channelPlayIdx;
}

// 검색이벤트
let ajaxSearch = document.querySelector('.ajaxSearch');
  
ajaxSearch.addEventListener(mousedownEvent, ()=>{
	
	const searchWord = document.getElementById('searchWord').value;
	if(searchWord == null || searchWord == ""){
		return;
	}
	
	const channelVal =  document.getElementById('no').checked;
	if( channelVal == true){
		const channelSearch = document.createElement('style');
		channelSearch.style.display="none";
	}
	
	//채널 검색 ajax
	$.ajax({
		url:'/channel/play/team/search?searchWord=' + searchWord,
		type : "post",
		datatype : "json",
		success: function(data){
			console.log("success!!");
			console.log(data)
			
        	openChannelPop(data);
		},
		error: function(textStatus, errorThorwn){
			alert("Status: " + textStatus);
			alert("Error: " + errorThorwn);
		}
	})
})


//채널 검색결과 출력
function openChannelPop(data){
	document.querySelector('.popupChannel').classList.add('popup_up')
	shadow.classList.toggle('shadow_show')
	
	const channelPop = document.querySelector('#listDiv');
	channelPopinnerHTML = "";
	
	for(var i = 0; i<data.length; i++){
		let channelData = data[i];
        channelPopinnerHTML += `<div class="channelBox">
									<input type="radio" id="channel${channelData.channelIdx}" + class="channelRadio" value="${channelData.channelIdx}"  name="channelIdx">
								 	<label for="channel${channelData.channelIdx}" style="background-color: transparent;">
										<div class="player_name">
											<h1>${channelData.channelName}</h1>
											<h2>${channelData.regionName}</h2>
										</div>
									</label>
								</div>`
	}
	channelPop.innerHTML=channelPopinnerHTML;
}

//function dataMapping(Data){
//	const gameRecord = document.querySelector('#game_record');
//	gameRecord.innerHTML = " ";
//	gameRecord.innerHTML=`<div class="row">
//											<div class="subtitle_line">
//												<h1 class="subtitle"">경기기록</h1>
//												<a href="#">기록추가<div class="right_arrow"></div></a>
//											</div>
//											<div class="game_record" id="sucess_shoot">`;
//	
//	const successShoot = document.querySelector('#success_shoot');			
//	successShoot.innerHTML=" ";
//	for(let i =0; i<Data.lenght; i++){
//	let goalInfo = Data[i];
//	console.log(goalInfo);
//	successShoot.innerHTML=`<div class="team1">
//												<h1> </h1>
//												<h1>+${goalInfo}+</h1>
//											</div>`;
//
//	}
//	
//}

let userInfo = "";

//팀 추가
function insertChannel(){
	const teamNameA = document.getElementById('teamName').value;
	if(teamName == "" || teamName == null){
		alert("팀 이름을 입력해주세요")
		return
	}
	
	const teamTypeLen =  document.getElementsByName('team').length;
	var teamTypeA = "";
	for (var i=0; i<teamTypeLen; i++) {
        if (document.getElementsByName("team")[i].checked == true) {
            teamTypeA = document.getElementsByName("team")[i].value;
        }
    }
    
    const channelIdxLen =  document.getElementsByName('channelIdx').length;
	var channelIdxA = "";
	for (var i=0; i<channelIdxLen; i++) {
        if (document.getElementsByName("channelIdx")[i].checked == true) {
            channelIdxA = document.getElementsByName("channelIdx")[i].value;
        }
    }
  
  	const channelplayIdxA = this.channelPlayIdx;
  	params = {
		"teamName" : teamNameA, 
		"teamType" : teamTypeA,
		"channelIdx" : channelIdxA,
		"channelPlayIdx" : channelplayIdxA
	}
	
  	//return 
  	
  	//팀추가 ajax 및 채널 idx로 멤버리스트 받아오기
	$.ajax({
		url:'/channel/play/team/teaminsert',
		type : "post",
		data : JSON.stringify(params),
		datatype : "text",
		contentType: "application/json; charset=UTF-8",

		success: function(data){
			
			console.log("success!!");
        	if(data != null || data != ""){
				alert("insert ok")
				document.querySelector('.popupChannel').classList.remove('popup_up')
    			shadow.classList.toggle('shadow_show')
    			console.log(data);
    			userInfo = data.searchMemberList;
    			teamInfo = data.teamVO;
				channelMemberSelect(userInfo);
			}
		},
		
		error: function(textStatus, errorThorwn){
			alert("Status: " + textStatus);
			alert("Error: " + errorThorwn);
		}
	})
}
/*
function checkExistPlayer(data){
	if(data.length == 0){
		document.getElementById("existPlayerNone").style.display="block";
		document.getElementById("existPlayer").style.display="none";
	}else{
		document.getElementById("existPlayerNone").style.display="block";
		document.getElementById("existPlayer").style.display="none";
	}
	
	
}*/

//수정 필요한 선수선택 버튼
//let selectButton = document.querySelector('#selectButton');
//selectButton.addEventListener('click', test(e));

//받아온 멤버 선택
function channelMemberSelect(userInfo){
	userInfoData = userInfo;
	console.log(userInfo);
	let players = document.querySelector('#playersId');	
	let playersInnerHTML = "";
	
	       playersInnerHTML += `<button class="player_add_btn modal_open_btn">+선수추가</button>
                    <div class="player_none">
                        <h1>선수 추가 버튼을 눌러서<br>
                            선수를 추가해주세요</h1>
                    </div>
	`;
	for(var i = 0; i<userInfo.length; i++){
		let memberData = userInfo[i];
		
/*        playersInnerHTML += `<div class="players">
									<input type="checkbox" id="user${memberData.userIdx}"  class="popup_checked" radio" value="${memberData.userIdx}"  name="selectPlayer">
								 	<label for="user${memberData.userIdx}" class="popup_player player2" style="background-color: transparent; width: fit-content;">
										<div class="player_name">
											<h1>${memberData.memberNick}</h1>
											<h2>${memberData.memberPosition}</h2>
										</div>
									</label>
							</div>`;*/
	        playersInnerHTML += `<div class="players">
									<input type="checkbox" id="user${memberData.userIdx}"  value="${memberData.userIdx}"  name="selectPlayer">
								 	<label for="user${memberData.userIdx}" >
									 	<div class="popup_checked"></div>
									 	<div class="popup_player player2"></div>
									 	<div class="player_name">
											<h1>${memberData.memberNick}</h1>
											<h2>${memberData.memberPosition}</h2>
										</div>
									</label>
								</div>
							`;
	}
       playersInnerHTML += `<button class="main_btn btn_yellow" id="existPlayer" onclick="insertPlayer()">선택한 선수 추가</button>
	`;
	players.innerHTML = playersInnerHTML;
	
}
//검색기능
// 다운버튼 누르면 팝업 다운
let downBtnChannel = document.querySelector('.down-btn-channel');
downBtnChannel.addEventListener('click', ()=> {
    document.querySelector('.popupChannel').classList.remove('popup_up')
    shadow.classList.toggle('shadow_show')
})



/*
 function playerCheck(){
	const selectPlayerLength =  document.getElementsByName('selectPlayer').length;
		for (var i=0; i<selectPlayerLength; i++) {
        if (document.getElementsByName("selectPlayer")[i].checked == true) {
            selectPlayer = document.getElementsByName("selectPlayer")[i].value;
            
        	
        }
    }
	
}*/	
//	var userIdx = this.userIdx;
//	var teamIdx = this.teamIdx;
//	var teamPlayerIdx = this.teamPlayerIdx;


	
	
function insertPlayer(){
	console.log(userInfo);
	console.log(teamInfo);
	
	
	var selectPlayer = []; 
	const selectPlayerLength =  document.getElementsByName('selectPlayer').length;
	console.log(selectPlayerLength);
		for (var i=0; i<selectPlayerLength; i++) {
        if (document.getElementsByName("selectPlayer")[i].checked == true) {
            selectPlayer.push(document.getElementsByName("selectPlayer")[i].value);
            
        }
    }
    
	
	params = {
		"teamIdx" : teamInfo.teamIdx, 
		"channelPlayIdx" : teamInfo.channelPlayIdx,
		"selectPlayer" : selectPlayer,
		 "userIdxSize" : selectPlayer.length
	}
	console.log(params);
	
	$.ajax({
			url:'/channel/play/team/playerinsert',
			type : "post",
			data : JSON.stringify(params),
			//datatype : "text",
			contentType: "application/json; charset=UTF-8",
	
			success: function(data){
				console.log("success!!");
				console.log(data)
	        	if(data != null || data != ""){
					alert("선수가 추가되었습니다.")
				}
			},
			
			error: function(textStatus, errorThorwn){
				alert("Status: " + textStatus);
				alert("Error: " + errorThorwn);
			}
	})
}
