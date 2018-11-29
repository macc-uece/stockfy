package br.com.uece.frameworks.stockfy.util.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Create by Bruno Barbosa - 28/11/2018
 */
@Service
public class PaginationValue {

    private final int BUTTONS_TO_SHOW = 5;
    private final int INITIAL_PAGE = 0;
    private final int INITIAL_PAGE_SIZE = 10;
    private final int[] PAGE_SIZES = {5, 10, 20};

    private int evalPageSize;

    public PageRequest getPositionPagination(Optional<Integer> pageSize, Optional<Integer> page) {

        // Evaluate page size. If requested parameter is null, return initial
        // page size
        evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        return new PageRequest(evalPage, evalPageSize);
    }

    public PageRequest getPositionPagination(Optional<Integer> pageSize, Optional<Integer> page, Sort sort) {
        evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
        return new PageRequest(evalPage, evalPageSize, sort);
    }

    public Pager getPagerView(Integer totalPages, Integer numero) {
        return new Pager(totalPages, numero, BUTTONS_TO_SHOW);
    }

    public int[] getPAGE_SIZES() {
        return PAGE_SIZES;
    }

    public int getEvalPageSize() {
        return evalPageSize;
    }
}
