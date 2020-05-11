package sch.personal.backendmovie.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sch.personal.backendmovie.document.MovieDocument;
import sch.personal.backendmovie.http.request.MovieRequest;
import sch.personal.backendmovie.http.response.BaseResponse;
import sch.personal.backendmovie.http.response.LayerResponse;
import sch.personal.backendmovie.http.vo.Movie;
import sch.personal.backendmovie.repository.MovieRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public LayerResponse movieList(Pageable pageable, Integer mid, String name) {
        LayerResponse response = new LayerResponse();
        List<Movie> movieList = new ArrayList<>();
        MovieDocument document = new MovieDocument();
        if (mid != null) {
            document.setMid(mid);
        }
        if (StringUtils.hasText(name)) {
            document.setName(name);
        }
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<MovieDocument> movieDocumentExample = Example.of(document, matcher);   //查询条件设置Example
        Page<MovieDocument> movieDocumentPage = movieRepository.findAll(movieDocumentExample, pageable);    //条件分页查询
        response.setCount((int) movieRepository.count(movieDocumentExample));    //条件查询总量
        for (MovieDocument movieDocument : movieDocumentPage) {
            Movie movie = new Movie();
            BeanUtils.copyProperties(movieDocument, movie);   //把userDocument的值赋值给user
            movieList.add(movie);
        }
        response.setData(movieList);
        return response;
    }

    //添加电影
    public BaseResponse addMovie(MovieRequest request) {
        MovieDocument movieDocument = new MovieDocument();
        BeanUtils.copyProperties(request, movieDocument);
        movieRepository.save(movieDocument);
        return new BaseResponse();
    }

    //删除电影
    public BaseResponse deleteMovie(Integer mid) {
        movieRepository.deleteByMid(mid);
        return new BaseResponse();
    }

    //根据mid查询电影信息
    public BaseResponse getMovie(Integer mid) {
        BaseResponse response = new BaseResponse();
        MovieDocument movieDocument = movieRepository.findByMid(mid);
        Movie movie = new Movie();
        BeanUtils.copyProperties(movieDocument, movie);
        response.setVo(movie);
        return response;
    }

    public BaseResponse updateMovie(MovieRequest request) {
        MovieDocument movieDocument = movieRepository.findByMid(request.getMid());
        movieDocument.setName(request.getName());
        movieDocument.setDescri(request.getDescri());
        movieDocument.setTimeLong(request.getTimeLong());
        movieDocument.setShoot(request.getShoot());
        movieDocument.setIssue(request.getIssue());
        movieDocument.setLanguage(request.getLanguage());
        movieDocument.setGenres(request.getGenres());
        movieDocument.setDirector(request.getDirector());
        movieDocument.setActors(request.getActors());
        movieRepository.save(movieDocument);
        return new BaseResponse();
    }
}
