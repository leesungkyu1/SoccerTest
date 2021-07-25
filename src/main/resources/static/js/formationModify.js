class playerObject {
		constructor() {
			teamPlayerIdx: 0;
			teamPlayerFormationNumber: 0;
			teamPlayerName: '';
			memberImage: '';
		}
		get teamPlayerIdx() {
			return this._teamPlayerIdx;
		}
		set teamPlayerIdx(teamPlayerIdx) {
			this._teamPlayerIdx = teamPlayerIdx;
		}
		get teamPlayerFormationNumber() {
			return this._teamPlayerFormationNumber;
		}
		set teamPlayerFormationNumber(teamPlayerFormationNumber) {
			this._teamPlayerFormationNumber = teamPlayerFormationNumber;
		}
		get teamPlayerName() {
			return this._teamPlayerName;
		}
		set teamPlayerName(teamPlayerName) {
			if (teamPlayerName == undefined){
				this._teamPlayerName = '';
			} else {
				this._teamPlayerName = teamPlayerName;
			}
		}
		get memberImage() {
			return this._memberImage;
		}
		set memberImage(memberImage) {
			this._memberImage = memberImage;
		}
	}

	const formationModify = {
		modifyRequest: {
			// 포메이션 변경 및 선수들의 포메이션 항목 변경 (여기 할 차례)
			updateFormation: ()=>{
				/* let teamPlayerVOList = formationModify.modifyData.playerArr;
				let formation = document.getElementById("formation_modify").value;
				let channelIdx = (Number)(document.getElementById("channelIdx").value);
				let channelPlayIdx = (Number)(document.getElementById("channelPlayIdx").value);
				let teamType = document.getElementById("teamType").value;
				
				try {
					let resp = await fetch('localhost:8080/channel/play/formation/' + channelIdx + '/' + channelPlayIdx + '/' + teamType, {
						method:'PUT',
						headers:{
							'Content-Type':'application/json;charset=utf-8'
						},
						body: JSON.stringify({
							'formation': formation,
							'channelIdx': channelIdx,
							'channelPlayIdx': channelPlayIdx,
							'teamType': teamType,
							'teamPlayerVOList': teamPlayerVOList
						})
					});
					let result = await resp.text();
					console.log(result);
					if (result == 'success') {
						return 
					} else {
						return ;
					}
				} catch (error){
					console.log(error);
				} */
				const updateBtn = document.querySelector('.main_btn')
				updateBtn.addEventListener('click', () => {
					
					console.log("javascript 진입");
					let teamPlayerVOList = formationModify.modifyData.playerArr;
					let formation = document.getElementById("formation_modify").value;
					let channelIdx = (Number)(document.getElementById("channelIdx").value);
					let channelPlayIdx = (Number)(document.getElementById("channelPlayIdx").value);
					let teamType = document.getElementById("teamType").value;
					
					let formData = document.getElementById('updateForm');
					
					let input2El = document.createElement('input');
					input2El.setAttribute('name', 'formation');
					input2El.setAttribute('type', 'hidden');
					input2El.setAttribute('value', formation);
					formData.appendChild(input2El);
					
					let input3El = document.createElement('input');
					input3El.setAttribute('name', 'channelIdx');
					input3El.setAttribute('type', 'hidden');
					input3El.setAttribute('value', channelIdx);
					formData.appendChild(input3El);
					
					let input4El = document.createElement('input');
					input4El.setAttribute('name', 'channelPlayIdx');
					input4El.setAttribute('type', 'hidden');
					input4El.setAttribute('value', channelPlayIdx);
					formData.appendChild(input4El);
					
					let input5El = document.createElement('input');
					input5El.setAttribute('name', 'teamType');
					input5El.setAttribute('type', 'hidden');
					input5El.setAttribute('value', teamType);
					formData.appendChild(input5El);
					
					/* let input6El = document.createElement('input');
					input6El.setAttribute('type', 'hidden');
					input6El.setAttribute('name', '_method');
					input6El.setAttribute('value', 'put');
					formData.appendChild(input6El); */
					
					for (let i = 0; i < teamPlayerVOList.length; i++){
						let inputEl1 = document.createElement('input');
						inputEl1.setAttribute('name', 'teamPlayerVOList[' + i + '].teamPlayerIdx');
						inputEl1.setAttribute('type', 'hidden');
						inputEl1.setAttribute('value', teamPlayerVOList[i].teamPlayerIdx);
						formData.appendChild(inputEl1);
						
						let inputEl2 = document.createElement('input');
						inputEl2.setAttribute('name', 'teamPlayerVOList[' + i + '].teamPlayerFormationNumber');
						inputEl2.setAttribute('type', 'hidden');
						inputEl2.setAttribute('value', teamPlayerVOList[i].teamPlayerFormationNumber);
						formData.appendChild(inputEl2);
						
					}
					
					/* formData.append('teamPlayerVOList', teamPlayerVOList);
					formData.append('formation', formation);
					formData.append('channelIdx', channelIdx);
					formData.append('channelPlayIdx', channelPlayIdx);
					formData.append('teamType', teamType);
					formData.action='/channel/play/formation/' + channelIdx + '/' + channelPlayIdx + '/' + teamType;
					formData.method='PUT'; */
					console.log(formData.action);
					formData.submit();
				});
				
			},
		},
		modifyView: {
			init: () => {
				formationModify.modifyView.popupView();

				formationModify.modifyView.modifyPersonView();
				
				// formationModify.modifyEvent.submitUpdateClick();
				
				formationModify.modifyRequest.updateFormation();
			},
			modifyFormationView: () => {
				const formation = document.getElementById("formation_modify").value;
				let goalkeeper = 1;
				let defender = 0;
				let midfilder = 0;
				let forward = 0;

				if (formation.length === 1) {
					forward = (Number)(formation.charAt(0));
				} else if (formation.length === 2) {
					defender = (Number)(formation.charAt(0));
					forward = (Number)(formation.charAt(1));
				} else if (formation.length >= 3) {
					defender = (Number)(formation.charAt(0));
					midfilder = (Number)(formation.charAt(1));
					for (let i = 3; i <= formation.length; i++) {
						forward += (Number)(formation.charAt(i - 1));
					}
				}

				const modifyFormationForm = document.getElementsByClassName("player")[0];

				const lin1El = document.createElement("div");
				const lin2El = document.createElement("div");
				const lin3El = document.createElement("div");
				const lin4El = document.createElement("div");
				lin1El.setAttribute("class", "lin1");
				lin2El.setAttribute("class", "lin2");
				lin3El.setAttribute("class", "lin3");
				lin4El.setAttribute("class", "lin4");
				for (let i = 0; i < goalkeeper; i++) {
					const divEl = document.createElement("div");
					const player0El = document.createElement("div");
					player0El.setAttribute("class", "player0");
					const teamPlayerNameEl = document.createElement("h1");
					const teamPlayerFormationNumberEl = document.createElement("input");
					teamPlayerFormationNumberEl.setAttribute("id", "teamPlayerFormationNumber");
					teamPlayerFormationNumberEl.setAttribute("type", "hidden");
					teamPlayerFormationNumberEl.setAttribute("value", i);
					const teamPlayerIdxEl = document.createElement("input");
					teamPlayerIdxEl.setAttribute("type", "hidden");

					divEl.appendChild(player0El);
					divEl.appendChild(teamPlayerNameEl);
					divEl.appendChild(teamPlayerFormationNumberEl);
					divEl.appendChild(teamPlayerIdxEl);

					lin1El.appendChild(divEl);
					modifyFormationForm.innerHTML = "";
					modifyFormationForm.appendChild(lin1El);
				}

				if (goalkeeper != goalkeeper + defender) {
					for (let i = goalkeeper; i < goalkeeper + defender; i++) {
						const divEl = document.createElement("div");
						const player0El = document.createElement("div");
						player0El.setAttribute("class", "player0");
						const teamPlayerNameEl = document.createElement("h1");
						const teamPlayerFormationNumberEl = document.createElement("input");
						teamPlayerFormationNumberEl.setAttribute("id", "teamPlayerFormationNumber");
						teamPlayerFormationNumberEl.setAttribute("value", i);
						teamPlayerFormationNumberEl.setAttribute("type", "hidden");
						const teamPlayerIdxEl = document.createElement("input");
						teamPlayerIdxEl.setAttribute("type", "hidden");

						divEl.appendChild(player0El);
						divEl.appendChild(teamPlayerNameEl);
						divEl.appendChild(teamPlayerFormationNumberEl);
						divEl.appendChild(teamPlayerIdxEl);

						lin2El.appendChild(divEl);
						modifyFormationForm.appendChild(lin2El);
					}
				}
				if (goalkeeper + defender != goalkeeper + defender + midfilder) {
					for (let i = goalkeeper + defender; i < goalkeeper + defender + midfilder; i++) {
						const divEl = document.createElement("div");
						const player0El = document.createElement("div");
						player0El.setAttribute("class", "player0");
						const teamPlayerNameEl = document.createElement("h1");
						const teamPlayerFormationNumberEl = document.createElement("input");
						teamPlayerFormationNumberEl.setAttribute("id", "teamPlayerFormationNumber");
						teamPlayerFormationNumberEl.setAttribute("value", i);
						teamPlayerFormationNumberEl.setAttribute("type", "hidden");
						const teamPlayerIdxEl = document.createElement("input");
						teamPlayerIdxEl.setAttribute("type", "hidden");

						divEl.appendChild(player0El);
						divEl.appendChild(teamPlayerNameEl);
						divEl.appendChild(teamPlayerFormationNumberEl);
						divEl.appendChild(teamPlayerIdxEl);

						lin3El.appendChild(divEl);
						modifyFormationForm.appendChild(lin3El);
					}
				}
				if (goalkeeper + defender + midfilder != goalkeeper + defender + midfilder + forward) {
					for (let i = goalkeeper + defender + midfilder; i < goalkeeper + defender + midfilder + forward; i++) {
						const divEl = document.createElement("div");
						const player0El = document.createElement("div");
						player0El.setAttribute("class", "player0");
						const teamPlayerNameEl = document.createElement("h1");
						const teamPlayerFormationNumberEl = document.createElement("input");
						teamPlayerFormationNumberEl.setAttribute("id", "teamPlayerFormationNumber");
						teamPlayerFormationNumberEl.setAttribute("value", i);
						teamPlayerFormationNumberEl.setAttribute("type", "hidden");
						const teamPlayerIdxEl = document.createElement("input");
						teamPlayerIdxEl.setAttribute("type", "hidden");

						divEl.appendChild(player0El);
						divEl.appendChild(teamPlayerNameEl);
						divEl.appendChild(teamPlayerFormationNumberEl);
						divEl.appendChild(teamPlayerIdxEl);

						lin4El.appendChild(divEl);
						modifyFormationForm.appendChild(lin4El);
					}
				}
				
				formationModify.modifyData.playerArr.length = 0;
				formationModify.modifyData.playerArr.length = goalkeeper + defender + midfilder + forward;
				for (let i = 0; i < formationModify.modifyData.playerArr.length; i++){
					const tmpClass = new playerObject();
					formationModify.modifyData.playerArr[i] = tmpClass;
				}

				formationModify.modifyView.init();
			},

			popupView: () => {
				let shadow = document.querySelector('.shadow');

				// 다운버튼 누르면 팝업 다운
				let downBtn = document.querySelector('.down-btn');
				downBtn.addEventListener('click', () => {
					document.querySelector('.popup').classList.remove('popup_up');
					shadow.classList.toggle('shadow_show');
				});

				// 맴버 누르면 팝업 업
				let player = Array.prototype.slice.call(document.querySelectorAll('.player > div > div > div'));
				for (let i = 0; i < player.length; i++) {
					const p = player[i];
					p.addEventListener('click', () => {
						playerClass = new playerObject();
						playerClass.teamPlayerFormationNumber = (Number)(p.nextElementSibling.nextElementSibling.value);
						console.log(playerClass);
						document.querySelector('.popup').classList.add('popup_up');
						//shadow.classList.toggle('shadow_show');
						shadow.classList.add('shadow_show');
					});
				}

			},

			modifyPersonView: () => {
				let shadow = document.querySelector('.shadow');
				const popupPlayers = document.getElementsByClassName("popup_players")[0].children;

				for (let i = 0; i < popupPlayers.length; i++) {
					popupPlayers[i].addEventListener('click', () => {
						// playerClass.memberImage = popupPlayers[i].firstChild. // 이미지
						playerClass.teamPlayerIdx = (Number)(popupPlayers[i].lastElementChild.firstElementChild.value);
						playerClass.teamPlayerName = popupPlayers[i].lastElementChild.firstElementChild.nextElementSibling.textContent;
						console.log(playerClass);

						const tmpClass = new playerObject();
						let checkExistArrIndex = -1;
						for (let j = 0; j < formationModify.modifyData.playerArr.length; j++) {
							const tmpObject = formationModify.modifyData.playerArr[j];
							if (tmpObject.teamPlayerIdx === playerClass.teamPlayerIdx) {
								checkExistArrIndex = j;
								tmpClass.teamPlayerIdx = formationModify.modifyData.playerArr[playerClass.teamPlayerFormationNumber].teamPlayerIdx;
								tmpClass.teamPlayerName = formationModify.modifyData.playerArr[playerClass.teamPlayerFormationNumber].teamPlayerName;
								tmpClass.teamPlayerFormationNumber = (Number)(j);
								break;
							}
						}
						console.log('tmpClass');
						console.log(tmpClass);

						if (checkExistArrIndex === -1) {
							formationModify.modifyData.playerArr[playerClass.teamPlayerFormationNumber] = playerClass;

							//console.log(formationModify.modifyData.playerArr);

							document.querySelector('.popup').classList.remove('popup_up');
							//shadow.classList.toggle('shadow_show');
							shadow.classList.remove('shadow_show');

							const modifying = document.querySelectorAll('#teamPlayerFormationNumber')[playerClass.teamPlayerFormationNumber];
							// modifying.previousElementSibling.previousElementSibling // 이미지 변경 부분

							//console.log(modifying.previousElementSibling.innerText);
							modifying.previousElementSibling.innerText = playerClass.teamPlayerName;
							//console.log(playerClass.teamPlayerName);
							//console.log(modifying.previousElementSibling.innerText);
							modifying.nextElementSibling.value = playerClass.teamPlayerIdx;

						} else {
							formationModify.modifyData.playerArr[playerClass.teamPlayerFormationNumber] = playerClass;
							formationModify.modifyData.playerArr[tmpClass.teamPlayerFormationNumber] = tmpClass;

							console.log(formationModify.modifyData.playerArr);

							document.querySelector('.popup').classList.remove('popup_up');
							//shadow.classList.toggle('shadow_show');
							shadow.classList.remove('shadow_show');

							const modifying1 = document.querySelectorAll('#teamPlayerFormationNumber')[playerClass.teamPlayerFormationNumber];
							// modifying.previousElementSibling.previousElementSibling // 이미지 변경 부분

							console.log(modifying1.previousElementSibling.innerText);
							modifying1.previousElementSibling.innerText = playerClass.teamPlayerName;
							console.log(playerClass.teamPlayerName);
							console.log(modifying1.previousElementSibling.innerText);
							modifying1.nextElementSibling.value = playerClass.teamPlayerIdx;

							const modifying2 = document.querySelectorAll('#teamPlayerFormationNumber')[tmpClass.teamPlayerFormationNumber];
							// modifying.previousElementSibling.previousElementSibling // 이미지 변경 부분

							console.log(modifying2.previousElementSibling.innerText);
							modifying2.previousElementSibling.innerText = tmpClass.teamPlayerName;
							console.log(tmpClass.teamPlayerName);
							console.log(modifying2.previousElementSibling.innerText);
							modifying2.nextElementSibling.value = tmpClass.teamPlayerIdx;
						}
					});
				}
			},

		},
		modifyEvent: {
			submitUpdateClick: () => {
				
				/* const updateBtn = document.querySelector('.main_btn')
				updateBtn.addEventListener('click', formationModify.modifyRequest.updateFormation());
				} */
			}
		},
		modifyData: {
			playerArr: [],

		}
	}


	const formation = document.getElementById("formation_modify").value;
	let goalkeeper = 1;
	let defender = 0;
	let midfilder = 0;
	let forward = 0;

	if (formation.length === 1) {
		forward = (Number)(formation.charAt(0));
	} else if (formation.length === 2) {
		defender = (Number)(formation.charAt(0));
		forward = (Number)(formation.charAt(1));
	} else if (formation.length >= 3) {
		defender = (Number)(formation.charAt(0));
		midfilder = (Number)(formation.charAt(1));
		for (let i = 3; i <= formation.length; i++) {
			forward += (Number)(formation.charAt(i - 1));
		}
	}

	for (let i = 0; i < goalkeeper; i++) {
		const lin1El = document.querySelectorAll('.lin1 > div')[i];
		playerClass = new playerObject();
		// playerClass.memberImage = lin1El.children[i]. // 이미지
		playerClass.teamPlayerName = lin1El.children[1].textContent;
		playerClass.teamPlayerFormationNumber = (Number)(lin1El.children[2].value);
		playerClass.teamPlayerIdx = (Number)(lin1El.children[3].value);
		formationModify.modifyData.playerArr[playerClass.teamPlayerFormationNumber] = playerClass;
		console.log(playerClass);
		console.log(formationModify.modifyData.playerArr);
		console.log(playerClass.teamPlayerFormationNumber);
	}

	if (goalkeeper != goalkeeper + defender) {
		for (let i = 0; i < defender; i++) {
			const lin2El = document.querySelectorAll('.lin2 > div')[i];
			playerClass = new playerObject();
			// playerClass.memberImage = lin2El.children[i]. // 이미지
			playerClass.teamPlayerName = lin2El.children[1].textContent;
			playerClass.teamPlayerFormationNumber = (Number)(lin2El.children[2].value);
			playerClass.teamPlayerIdx = (Number)(lin2El.children[3].value);
			formationModify.modifyData.playerArr[playerClass.teamPlayerFormationNumber] = playerClass;
			console.log(playerClass);
			console.log(formationModify.modifyData.playerArr);
			console.log(playerClass.teamPlayerFormationNumber);
		}
	}

	if (goalkeeper + defender != goalkeeper + defender + midfilder) {
		for (let i = 0; i < midfilder; i++) {
			const lin3El = document.querySelectorAll('.lin3 > div')[i];
			playerClass = new playerObject();
			// playerClass.memberImage = lin3El.children[i]. // 이미지
			playerClass.teamPlayerName = lin3El.children[1].textContent;
			playerClass.teamPlayerFormationNumber = (Number)(lin3El.children[2].value);
			playerClass.teamPlayerIdx = (Number)(lin3El.children[3].value);
			formationModify.modifyData.playerArr[playerClass.teamPlayerFormationNumber] = playerClass;
			console.log(playerClass);
			console.log(formationModify.modifyData.playerArr);
			console.log(playerClass.teamPlayerFormationNumber);
		}
	}

	if (goalkeeper + defender + midfilder != goalkeeper + defender + midfilder + forward) {
		for (let i = 0; i < forward; i++) {
			const lin4El = document.querySelectorAll('.lin4 > div')[i];
			playerClass = new playerObject();
			// playerClass.memberImage = lin4El.children[i]. // 이미지
			playerClass.teamPlayerName = lin4El.children[1].textContent;
			playerClass.teamPlayerFormationNumber = (Number)(lin4El.children[2].value);
			playerClass.teamPlayerIdx = (Number)(lin4El.children[3].value);
			formationModify.modifyData.playerArr[playerClass.teamPlayerFormationNumber] = playerClass;
			console.log(playerClass);
			console.log(formationModify.modifyData.playerArr);
			console.log(playerClass.teamPlayerFormationNumber);
		}
	}

	formationModify.modifyView.init();
			        