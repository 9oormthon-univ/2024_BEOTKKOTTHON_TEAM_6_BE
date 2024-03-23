package org.goormthon.beotkkotthon.rebook.service.quizhistory;

import lombok.RequiredArgsConstructor;
import org.goormthon.beotkkotthon.rebook.domain.*;
import org.goormthon.beotkkotthon.rebook.dto.response.studyhistory.ImageAnalysisDto;
import org.goormthon.beotkkotthon.rebook.exception.CommonException;
import org.goormthon.beotkkotthon.rebook.exception.ErrorCode;
import org.goormthon.beotkkotthon.rebook.repository.*;
import org.goormthon.beotkkotthon.rebook.usecase.studyhistory.CreateImageAnalysisUseCase;
import org.goormthon.beotkkotthon.rebook.utility.AnalysisUtil;
import org.goormthon.beotkkotthon.rebook.utility.ImageUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateImageAnalysisService implements CreateImageAnalysisUseCase {
    private final ImageUtil imageUtil;
    private final AnalysisUtil analysisUtil;
    private final UserRepository userRepository;
    private final RecycleCategoryRepository recycleCategoryRepository;
    private final ChallengeRoomRepository challengeRoomRepository;
    private final ChallengeRoomStudyHistoryRepository challengeRoomStudyHistoryRepository;
    private final StudyHistoryRepository studyHistoryRepository;

    @Transactional
    @Override
    public ImageAnalysisDto execute(UUID userId, MultipartFile image) {
        String imageName = imageUtil.uploadImage(image);
        String imageUrl = imageUtil.getImageUrl(imageName);

        List<String> imageAnalysisList = analysisUtil.getImageAnalysis(image);
        String category = analysisUtil.getImageRecycleCategory(imageAnalysisList);
        RecycleCategory recycleCategory = recycleCategoryRepository.findByName(category)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

//        System.out.println(recycleCategory);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        StudyHistory studyHistory = studyHistoryRepository.save(
                StudyHistory.builder()
                        .imageUrl(imageUrl)
                        .user(user)
                        .recycleCategory(recycleCategory)
                        .build());

        Optional<ChallengeRoom> challengeRoom = challengeRoomRepository.findByUser(user, LocalDateTime.now());

        if (challengeRoom.isPresent()) {
            Optional<ChallengeRoomStudyHistory> challengeRoomStudyHistory = challengeRoomStudyHistoryRepository.findByChallengeRoom(challengeRoom.get());
            if (challengeRoomStudyHistory.isEmpty()) {

                challengeRoomStudyHistoryRepository.save(
                        ChallengeRoomStudyHistory.builder()
                                .challengeRoom(challengeRoom.get())
                                .studyHistory(studyHistory)
                                .build());

                user.updateEnvironmentalTemperature(user.getEnvironmentalTemperature() + 0.1f);

                return ImageAnalysisDto.builder()
                        .completeTodayCurrentChallenge(true)
                        .recycleCategory(studyHistory.getRecycleCategory().getName())
                        .information(studyHistory.getRecycleCategory().getDescription())
                        .created_at(studyHistory.getCreatedAt().toString())
                        .build();
            }
        }

    return ImageAnalysisDto.builder()
            .completeTodayCurrentChallenge(false)
            .recycleCategory(studyHistory.getRecycleCategory().getName())
            .information(studyHistory.getRecycleCategory().getDescription())
            .created_at(studyHistory.getCreatedAt().toString())
            .build();
    }
}
