package com.project.interfaces.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadMultipleFilesResponseDTO {
	
	private List<UploadFileResponse> uploadFileResponses;

}
