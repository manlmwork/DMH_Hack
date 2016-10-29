/**
 * Created by manlm on 9/10/2016.
 */
$(document).ready(function () {
    $("#user-data-table").bootgrid({
        css: {
            icon: 'zmdi icon',
            iconColumns: 'zmdi-view-module',
            iconDown: 'zmdi-expand-more',
            iconRefresh: 'zmdi-refresh',
            iconUp: 'zmdi-expand-less'
        },
    });
});