  

function goDetailView(channelIdx, channelPlayIdx){
	
	console.log(channelIdx, channelPlayIdx);
		location.href = '/channel/play/'+channelIdx+"/"+channelPlayIdx;
}

//const test1 = document.querySelector('#test1');
//test1.addEventListener('click', function goDetailView(channelIdx, channelPlayIdx){
//	console.log('클릭했음');
//
//})

//function playResultMove(channelIdx){
//	location.href='/channel/play/goal/'+channelIdx;
//}

function testAjax(channelPlayIdx) {
	$.ajax({
		url:`/channel/play/goal/${channelPlayIdx}`,
		
		success: function(Data){
			console.log("success!!");
			console.log(Data);
			dataMapping(Data);
		},
		error: function(textStatus, errorThorwn){
			alert("Status: " + textStatus);
			alert("Error: " + errorThorwn);
		}
	})
}

function dataMapping(Data){
	const gameRecord = document.querySelector('#game_record');
	gameRecord.innerHTML = " ";
	gameRecord.innerHTML=`<div class="row">
											<div class="subtitle_line">
												<h1 class="subtitle"">경기기록</h1>
												<a href="#">기록추가<div class="right_arrow"></div></a>
											</div>
											<div class="game_record" id="sucess_shoot">`;
	
	const successShoot = document.querySelector('#success_shoot');			
	successShoot.innerHTML=" ";
	for(let i =0; i<Data.lenght; i++){
	let goalInfo = Data[i];
	console.log(goalInfo);
	successShoot.innerHTML=`<div class="team1">
												<h1> </h1>
												<h1>+${goalInfo}+</h1>
											</div>`;

	}
	
}