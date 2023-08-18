package com.vitrum.api.service;

import com.vitrum.api.dto.Request.TopicRequest;
import com.vitrum.api.dto.Response.TopicResponse;
import com.vitrum.api.entity.Course;
import com.vitrum.api.entity.Topic;
import com.vitrum.api.repository.CourseRepository;
import com.vitrum.api.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository repository;
    private final CourseRepository courseRepository;

    public List<TopicResponse> getAllTopics() {
        List<Topic> topics = repository.findAll();
        List<TopicResponse> topicResponses = new ArrayList<>();

        for (Topic topic : topics) {
            topicResponses.add(mapTopicToTopicResponse(topic));
        }
        return topicResponses;
    }

    public List<TopicResponse> getAllTopicsByCourse(Course course) {
        Optional<List<Topic>> topics = repository.findByCourse(course);
        List<TopicResponse> topicResponses = new ArrayList<>();

        if (topics.isPresent()) {
            for (Topic topic : topics.get()) {
                topicResponses.add(mapTopicToTopicResponse(topic));
            }
        }
        return topicResponses;
    }

    public TopicResponse createTopic(TopicRequest topicRequest, Course course) {
        var topic = Topic.builder()
                .name(topicRequest.getName())
                .description(topicRequest.getDescription())
                .additionalResources(topicRequest.getAdditionalResources())
                .course(course)
                .build();

        repository.save(topic);

        return TopicResponse.builder()
                .id(topic.getId())
                .name(topic.getName())
                .description(topic.getDescription())
                .additionalResources(topic.getAdditionalResources())
                .courseId(topic.getCourse().getId())
                .build();
    }

    public Course getCourseByName(String name) {
        Optional<Course> optionalCourse = courseRepository.findByName(name);
        return optionalCourse.orElse(null);
    }

    private TopicResponse mapTopicToTopicResponse(Topic topic) {
        return TopicResponse.builder()
                .id(topic.getId())
                .name(topic.getName())
                .description(topic.getDescription())
                .additionalResources(topic.getAdditionalResources())
                .courseId(topic.getCourse().getId())
                .build();
    }
}
