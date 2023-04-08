<#import "parts/common.ftlh" as base>

<@base.page>

<div class="eq-container">
    <div class="eq-section-1">
        <button class="eq-back">
            <a href="/equipment-units"><img src="../static/images/Union.svg"></a>
        </button>
    </div>

    <div class="eq-section-2">

        <div class="section-qr">

            <div class="img-box">
                <img src="/equipment-units/${current.equipmentUnitId}/qr-code">
            </div>
            <div class="eq-qr-buttons">
                <button class="btn-print btn">
                    <img class="icon-white" src="../static/images/print Green.svg">
                    Печать
                </button>
                <button class="btn-qr-save btn">
                    <img class="icon-white" src="../static/images/down Green.svg">
                    Сохранить
                </button>
            </div>

        </div>

        <div class="eq-forma">
            <div class="divspan div2-span2">
                <span class="span2">Тип</span>
                <form action="/equipment-units/${current.equipmentUnitId}/type-change" method="post">
                    <select class="Type_class first custom-select link-select" name="type" onchange="this.form.submit()">
                        <#list equipmentTypes as equipmentType>
                            <#if equipmentType.equals(currentEquipmentType)>
                                <option class="op1" value="${equipmentType.equipmentTypeId}" selected>${equipmentType.equipmentTypeName}</option>
                            <#else>
                                <option class="op2" value="${equipmentType.equipmentTypeId}">${equipmentType.equipmentTypeName}</option>
                            </#if>
                        </#list>
                    </select>
                </form>
            </div>
            <form method="post" action="/equipment-units/${current.equipmentUnitId}">
                <div class="divspan div3-span3">
                    <span class="span3">Оборудование</span>
                    <select class="Equipment_class first custom-select" name="equipment">
                        <#list equipments as equipment>
                            <#if equipment.equals(current.equipment)>
                                <option class="op1" value="${equipment.equipmentId}" selected>${equipment.modelName}</option>
                            <#else>
                                <option class="op2" value="${equipment.equipmentId}">${equipment.modelName}</option>
                            </#if>
                        </#list>
                    </select>
                </div>
                <div class="divspan div1-span1">
                    <span class="span1">Инвентарный номер</span>
                    <input class="invNumber_class" name="invNumber" type="number" placeholder="Инвентарный номер"
                           value="${current.inventoryNumber}">
                </div>
                <div class="divspan div4-span4">
                    <span class="span4">Корпус</span>
                    <select class="Corpus_class first custom-select" id="Corpus" name="corpus">
                        <#list trainingCenters as trainingCenter>
                            <#if trainingCenter.equals(current.location.trainingCenter)>
                                <option class="op1" value="${trainingCenter.trainingCenterId}" selected>${trainingCenter.centerAddress}</option>
                            <#else>
                                <option class="op2" value="${trainingCenter.trainingCenterId}">${trainingCenter.centerAddress}</option>
                            </#if>
                        </#list>
                    </select>
                </div>

                    <div class="divspan div5-span5">
                        <span class="span5">Аудитория</span>
                        <input class="Audience_class" value="${audience.locationNumber}" type="text"/>
                    </div>
                    <div class="divspan div6-span6">
                        <span class="span6">Наличие</span>
                        <#if current.onState>
                            <input type="checkbox" class="chek custom-control-input" id="inPlace" name="onStateAsString" checked>
                        <#else>
                            <input type="checkbox" class="chek custom-control-input" id="inPlace" name="onStateAsString">
                        </#if>
                    </div>

                    <div class="eq-form-buttons">
                        <button class="eq-btn-delete btn">
                            Удалить
                        </button>
                        <button class="eq-btn-save btn" type="submit">Сохранить</button>
                    </div>
                </form>
        </div>

    </div>

</div>

</@base.page>