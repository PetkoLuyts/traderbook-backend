package com.example.traderbookv2;

import com.example.traderbookv2.model.CommentEntity;
import com.example.traderbookv2.model.PostEntity;
import com.example.traderbookv2.model.StatusEntity;
import com.example.traderbookv2.model.UserEntity;
import com.example.traderbookv2.repository.CommentRepository;
import com.example.traderbookv2.repository.PostRepository;
import com.example.traderbookv2.repository.StatusRepository;
import com.example.traderbookv2.repository.UserRepository;
import com.example.traderbookv2.service.CommentService;
import com.example.traderbookv2.service.PostService;
import com.example.traderbookv2.service.StatusService;
import com.example.traderbookv2.service.UserService;
import com.example.traderbookv2.web.CommentController;
import com.example.traderbookv2.web.PostController;
import com.example.traderbookv2.web.StatusController;
import com.example.traderbookv2.web.UserController;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.Array;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Traderbookv2ApplicationTests extends AbstractContainerBaseTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MockMvc mockMvc;

    // given/ when/ then format - BDD style
    @Test
    public void givenUser_whenGetUser_thenUsers() throws Exception {
        // given - setup
        // int id, String userId, String userName, String name, String profileImage
        UserEntity user = new UserEntity(1, "123", "petko_lyuts", "petko", "blob");
        userRepository.save(user);

        // when - action
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/users/1"));

        // then - verify the output
        response.andExpect(status().isOk());
    }

    @Test
    public void givenAllStatus_whenGetAllStatus_thenAllStatus() throws Exception {
        // given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        StatusEntity status = new StatusEntity(1, "10", "123", "somepath", timestamp);
        statusRepository.save(status);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/status"));

        // then
        response.andExpect(status().isOk());
    }

    @Test
    public void givelAllPost_whenGetAllPost_thenAllPost() throws Exception {
        // given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PostEntity post = new PostEntity(1, "777", "123", "somepath123", timestamp, 13);
        postRepository.save(post);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/post"));

        // then
        response.andExpect(status().isOk());
    }

    // int id, String commentId, String userId, String postId, Timestamp timestamp, String comment, String userName
    @Test
    public void giveComment_whenGetComment_thenComment() throws Exception {
        // given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CommentEntity comment = new CommentEntity
                (1, "17", "123", "777", timestamp,
                        "somecomment", "petko_lyuts");
        commentRepository.save(comment);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/comments/1"));

        // then
        response.andExpect(status().isOk());
    }

    @Test
    public void giveAllComments_whenGetAllComments_thenAllComments() {
        // given
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        List<CommentEntity> comments =
//                List.of(new CommentEntity(2, "17", "123", "777", timestamp,
//                        "somecomment", "petko_lyuts"),
//                        new CommentEntity(3, "17", "123", "777", timestamp,
//                                "somethirdcomment", "petko_lyuts"));
        CommentRepository mockedCommentRepository = mock(CommentRepository.class);
        CommentService commentService = new CommentService();
        commentService.commentRepository = mockedCommentRepository;
        CommentController commentController = new CommentController();
        commentController.commentService = commentService;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CommentEntity commentToSave = new CommentEntity(15, "150", "1001",
                "433", timestamp, "somecomment", "testusername");

        commentController.submitComment(commentToSave);
        ArrayList<CommentEntity> comments = commentService.getAllCommentsForDB("433");

        verify(mockedCommentRepository, times(1)).save(commentToSave);
    }

    // 3, "333", "user_new", "someuser", "blob123"
    @Test
    public void createUser() {
        UserEntity user = new UserEntity();
        user.setId(3);
        user.setUserId("333");
        user.setUserName("user_new");
        user.setName("someuser");
        user.setProfileImage("blob123");
        userRepository.save(user);

        Assertions.assertThat(user.getId()).isGreaterThan(0);
        Assertions.assertThat(user.getUserId()).isEqualTo("333");
        Assertions.assertThat(user.getUserName()).isEqualTo("user_new");
        Assertions.assertThat(user.getName()).isEqualTo("someuser");
        Assertions.assertThat(user.getProfileImage()).isEqualTo("blob123");
    }

    // int id, String statusId, String userId, String path, Timestamp timestamp
    @Test
    public void createStatus() {
        StatusEntity status = new StatusEntity();
        status.setId(3);
        status.setStatusId("737");
        status.setUserName("user_new");
        status.setUserId("333");
        status.setPath("somepath");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        status.setTimestamp(timestamp);
        statusRepository.save(status);

        Assertions.assertThat(status.getId()).isGreaterThanOrEqualTo(3);
        Assertions.assertThat(status.getStatusId()).isEqualTo("737");
        Assertions.assertThat(status.getUserName()).isEqualTo("user_new");
        Assertions.assertThat(status.getUserId()).isEqualTo("333");
        Assertions.assertThat(status.getPath()).isEqualTo("somepath");
        Assertions.assertThat(status.getTimestamp()).isEqualTo(timestamp);
    }

    // int id, String postId, String userId, String postPath, Timestamp timestamp, int likeCount
    @Test
    public void createPost() {
        PostEntity post = new PostEntity();
        post.setId(4);
        post.setPostId("432");
        post.setUserId("333");
        post.setUserName("user_new");
        post.setPostPath("somepath123");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        post.setTimestamp(timestamp);
        post.setLikeCount(30);
        postRepository.save(post);

        Assertions.assertThat(post.getId()).isGreaterThanOrEqualTo(4);
        Assertions.assertThat(post.getPostId()).isEqualTo("432");
        Assertions.assertThat(post.getUserId()).isEqualTo("333");
        Assertions.assertThat(post.getUserName()).isEqualTo("user_new");
        Assertions.assertThat(post.getPostPath()).isEqualTo("somepath123");
        Assertions.assertThat(post.getTimestamp()).isEqualTo(timestamp);
        Assertions.assertThat(post.getLikeCount()).isEqualTo(30);
    }

    // int id, String commentId, String userId, String postId, Timestamp timestamp, String comment, String userName
    @Test
    public void createComment() {
        CommentEntity comment = new CommentEntity();
        comment.setId(9);
        comment.setCommentId("777");
        comment.setUserId("333");
        comment.setPostId("432");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        comment.setTimestamp(timestamp);
        comment.setComment("somerandomcomment");
        comment.setUserName("user_new");
        commentRepository.save(comment);

        Assertions.assertThat(comment.getId()).isGreaterThanOrEqualTo(9);
        Assertions.assertThat(comment.getCommentId()).isEqualTo("777");
        Assertions.assertThat(comment.getUserId()).isEqualTo("333");
        Assertions.assertThat(comment.getPostId()).isEqualTo("432");
        Assertions.assertThat(comment.getTimestamp()).isEqualTo(timestamp);
        Assertions.assertThat(comment.getComment()).isEqualTo("somerandomcomment");
        Assertions.assertThat(comment.getUserName()).isEqualTo("user_new");
    }

     //int id, String userId, String userName, String name, String profileImage
    @Test
    public void shouldCreateUser() throws Exception {
        UserRepository mockedUserRepository = mock(UserRepository.class);
        UserService userService = new UserService();
        userService.userRepository = mockedUserRepository;
        UserController userController = new UserController();
        userController.userService = userService;
        UserEntity userToSave = new UserEntity(2, "1001", "testusername", "test", "blob");

        userController.submitUser(userToSave);
        verify(mockedUserRepository, times(1)).save(userToSave);
    }

    // int id, String statusId, String userId, String path, Timestamp timestamp
    @Test
    public void shouldCreateStatus() {
        StatusRepository mockedStatusRepository = mock(StatusRepository.class);
        StatusService statusService = new StatusService();
        statusService.statusRepository = mockedStatusRepository;
        StatusController statusController = new StatusController();
        statusController.statusService = statusService;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        StatusEntity statusToSave = new StatusEntity(3, "555", "1001", "path", timestamp);

        statusController.submitStatus(statusToSave);
        verify(mockedStatusRepository, times(1)).save(statusToSave);
    }

    // int id, String postId, String userId, String postPath, Timestamp timestamp, int likeCount
    @Test
    public void shouldCreatePost() {
        PostRepository mockedPostRepository = mock(PostRepository.class);
        PostService postService = new PostService();
        postService.postRepository = mockedPostRepository;
        PostController postController = new PostController();
        postController.postService = postService;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PostEntity postToSave = new PostEntity(10, "433", "1001", "path", timestamp, 30);

        postController.submitUserPost(postToSave);
        verify(mockedPostRepository, times(1)).save(postToSave);
    }

    // int id, String commentId, String userId, String postId, Timestamp timestamp, String comment, String userName
    @Test
    public void shouldCreateComment() {
        CommentRepository mockedCommentRepository = mock(CommentRepository.class);
        CommentService commentService = new CommentService();
        commentService.commentRepository = mockedCommentRepository;
        CommentController commentController = new CommentController();
        commentController.commentService = commentService;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CommentEntity commentToSave = new CommentEntity(15, "150", "1001",
                "433", timestamp, "somecomment", "testusername");

        commentController.submitComment(commentToSave);
        verify(mockedCommentRepository, times(1)).save(commentToSave);
    }

    @Test
    public void applicationStarts() {
        Traderbookv2Application.main(new String[] {});
    }
}
