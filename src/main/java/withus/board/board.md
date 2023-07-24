# Withus Board

```
board
 ├─BoardController: 메인컨트롤러
 ├─DataNotFoundExeption 
 ├─controller
 │  ├─PostController: 게시물 컨트롤러
 │  ├─CommentController: 댓글 컨트롤러
 │  └─RankController: 명예의전당 컨트롤러
 ├─entity
 │  ├─Post: 게시물 엔티티
 │  └─Comment: 댓글 엔티티
 ├─form
 │  ├─PostForm: 게시물 유효성 검사 폼
 │  └─CommentForm: 댓글 유효성 검사 폼
 ├─repository
 │  ├─PostRepository: 게시물 레포지터리
 │  └─CommentRepository: 댓글 레포지터리
 └─service
    ├─PostService: 게시물 서비스
    └─CommentService: 댓글 서비스
```