package cn.edu.cupk.learn.common.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 分页JSON
 *
 * @author Icy
 * @version 1.0.0
 * @since 版本
 */
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> {

    private List<T> records;

    private Integer pages;

    public PageVO(@NotNull IPage<T> res) {
        this.records = res.getRecords();
        this.pages = (int) res.getPages();
    }

}
