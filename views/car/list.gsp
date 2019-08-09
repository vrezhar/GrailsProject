<div id="list-car" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
            <tr>
                <g:sortableColumn
                    property="make"
                    title="${message(code: 'car.make.label', default: 'Make')}"
                 />
                <g:sortableColumn
                    property="model"
                    title="${message(code: 'car.model.label', default: 'Model')}"
               />
            </tr>
        </thead>
        <tbody>
            <g:each in="${carInstanceList}" status="i" var="carInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <td>
                        <g:link
                            action="show"
                            id="${carInstance.id}">${fieldValue(bean: carInstance, field: "make")}
                        </g:link>
                    </td>
                    <td> ${fieldValue(bean: carInstance, field: "model")} </td>
                </tr>
            </g:each>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${carInstanceTotal}" />
    </div>
</div>