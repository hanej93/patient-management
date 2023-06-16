let patient = {
	init: function(){
		$("#addPatientModal .save-btn").on("click",()=>{
			this.save();
		});

		// todo : 수정, 삭제 기능
		$("#savePatientModal .delete-btn").on("click",()=>{
			this.delete();
		});

		$("#updatePatientModal .update-btn").on("click",()=>{
			this.update();
		});
	},

	search: function (nowPage = 1) {
		const pageNo = nowPage;
		const pageSize = PAGE_SIZE;
		const schType = $("#schType").val();
		const keyword = $("#search-input").val();

		const data = {
			pageNo,
			pageSize,
			schType,
			keyword
		}

		$.ajax({
			url: '/api/patients',
			method: 'GET',
			data: data,
		}).done(result => {
			let paginationBox = $('.pagination-box');
			if (result.content.length === 0) {
				paginationBox.addClass('d-none');
				let noResultRow = $('<tr><td colspan="6" class="text-center py-3">검색결과가 없습니다.</td></tr>');
				$('.table tbody').empty().append(noResultRow);
				return;
			}
			paginationBox.removeClass('d-none');
			this.handleData(result);
			this.setPageInfo(result);
			this.handlePage(result);
		}).fail(xhr => {
			console.error(`searchPatients error: ${JSON.stringify(xhr)}`);
		})
	},

	clearModal: function ($modal) {
		$modal.find("input[name=patientName]").val('');
		$modal.find("select[name=hospital] option:first").prop('selected', true);
		$modal.find("input[name=genderCode]:first").prop('checked', true);
		$modal.find("input[name=dateOfBirth]").val('');
		$modal.find("input[name=mobilePhoneNumber]").val('');
	},

	save: function (){
		let patientName= $("#addPatientModal input[name=patientName]").val();
		let hospitalId = $("#addPatientModal select[name=hospital]").val();
		let genderCode= $("#addPatientModal input[name=genderCode]").val();
		let dateOfBirth = $("#addPatientModal input[name=dateOfBirth]").val();
		let mobilePhoneNumber = $("#addPatientModal input[name=mobilePhoneNumber]").val();

		let data = {
			patientName,
			hospitalId,
			genderCode,
			dateOfBirth,
			mobilePhoneNumber
		};

		$.ajax({
			type:"POST",
			url:"/api/patients",
			data:JSON.stringify(data),
			contentType:"application/json;charset=utf-8",
			dataType:"json"
		}).done(result => {
			console.log(result);
			const $addModal = $('#addPatientModal');
			$addModal.modal('hide');
			this.search();
			this.clearModal($addModal);
		}).fail(xhr => {
			console.error(`savePatients error: ${JSON.stringify(xhr)}`);
		});
	},

	delete: function (){
		let id = $("#id").text();

		$.ajax({
			type:"DELETE",
			url:"/api/patients/" + id,
			dataType:"json"
		}).done(result => {
			console.log(result);
			$('#deletePatientModal').modal('hide');
			this.search();
		}).fail(xhr => {
			console.error(`deletePatients error: ${JSON.stringify(xhr)}`);
		});
	},


	update: function (){
		let id = $("#id").val();

		let data = {
		};

		$.ajax({
			type:"PUT",
			url:"/api/patients/" + id,
			data:JSON.stringify(data),
			contentType:"application/json;charset=utf-8",
			dataType:"json"
		}).done(result => {
			console.log(result);
			$('#updatePatientModal').modal('hide');
			this.search();
		}).fail(xhr => {
			console.error(`updatePatients error: ${JSON.stringify(xhr)}`);
		});
	},

	handleData: function (data) {
		let tableBody = $('.table tbody');
		tableBody.empty();

		$.each(data.content, function(index, patient) {
			let row = $('<tr></tr>');
			let nameCell = $('<td></td>').text(patient.patientName);
			let registrationNumberCell = $('<td></td>').text(patient.patientRegistrationNumber);
			let genderCell = $('<td></td>').text(patient.genderCode);
			let dateOfBirthCell = $('<td></td>').text(patient.dateOfBirth);
			let phoneNumberCell = $('<td></td>').text(patient.mobilePhoneNumber);
			let recentlyVisitedCell = $('<td></td>').text(patient.recentlyVisited);

			row.append(nameCell, registrationNumberCell, genderCell, dateOfBirthCell, phoneNumberCell, recentlyVisitedCell);
			tableBody.append(row);
		});
	},

	setPageInfo: function (data) {
		let totalElements = data.totalElements;
		let pageNumber = data.number + 1; // 0-based index를 1-based index로 변경
		let pageSize = data.size;
		let startElement = (pageNumber - 1) * pageSize + 1;
		let endElement = Math.min(startElement + pageSize - 1, totalElements);

		let pageInfo = '총 ' + totalElements + '건 중 ' + startElement + ' - ' + endElement;
		$('.page-info').text(pageInfo);
	},

	handlePage: function (data) {
		// 페이지네이션 요소 생성
		let pagination = $('<ul class="pagination"></ul>');

		// 이전 버튼 생성
		if (data.number !== 0) {
			let previousButton = $('<li class="page-item previous"></li>');
			let previousLink = $('<a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a>');
			previousButton.append(previousLink);
			pagination.append(previousButton);
		}

		// 페이지 버튼 생성
		let currentPage = data.number + 1; // 0-based index로 전달된 현재 페이지 번호를 1-based index로 변경

		let startPageGroup = Math.floor((currentPage - 1) / 5) * 5 + 1;
		let endPageGroup = Math.min(startPageGroup + 4, data.totalPages);

		for (let i = startPageGroup; i <= endPageGroup; i++) {
			let pageItem = $('<li class="page-item"></li>');
			let pageLink = $('<a class="page-link" href="#">' + i + '</a>');

			if (i === currentPage) {
				pageItem.addClass('active');
			}

			pageItem.append(pageLink);
			pagination.append(pageItem);
		}

		// 다음 버튼 생성
		if (data.number !== data.totalPages - 1) {
			let nextButton = $('<li class="page-item next"></li>');
			let nextLink = $('<a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&raquo;</span></a>');
			nextButton.append(nextLink);
			pagination.append(nextButton);
		}

		// 생성한 페이지네이션 요소를 DOM에 추가
		$('.pagination-container').empty().append(pagination);

		// 아이템 리스트 생성
		let itemList = $('<ul class="item-list"></ul>');
		$.each(data.content, function(index, item) {
			let listItem = $('<li class="item">' + item.name + '</li>');
			itemList.append(listItem);
		});

		// 생성한 아이템 리스트를 DOM에 추가
		$('.item-list-container').empty().append(itemList);
	}
}
