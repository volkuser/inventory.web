<#import "parts/common.ftlh" as base>

<@base.page>

  <div class="container">
    <h1>Единицы оборудования</h1>
  </div>

  <section class="search-line">
    <div class="container">

      <div class="input-group">

        <form class="search-form" method="post" action="/equipment-units/search">
            <input type="search" class="form-control" placeholder="Поиск" aria-label="Search" aria-describedby="search-addon" name="searchQuery" value="${searchQuery}"/>
            <button class="btn btn-search" type="submit">Найти</button>
        </form>

        <p>
          <button class="btn btn-filtr" type="button" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
            Фильтр
          </button>
        </p>
        <button class="btn add" data-bs-toggle="modal" data-bs-target="#exampleModal" ></button>

        <div class="collapse" id="collapseExample">
          <form class="form-filtr" method="post" action="/equipment-units/filter">
            <div class="card card-body">
              <div class="column column-1">
              <select class="Corpus_class first custom-select" id="Corpus" name="Corpus">
                <#list trainingCenters as trainingCenter>
                  <option value="${trainingCenter.trainingCenterId}">${trainingCenter.centerAddress}</option>
                </#list>
              </select>
              <#--<input class="Type_class" name="Type" type="text" placeholder="Тип">-->
            </div>
              <div class="column column-2">

                <input class="Audience_class first" name="Audience" type="number" placeholder="Аудитория">
                <#--<input class="Equipment_class first custom-select" name="equipment" type="text" placeholder="Оборудование">-->

              </div>

              <div class="column column-3">

                <#--<div class="inPlace_class custom-control custom-checkbox">
                  <label class="inPlace_label custom-control-label" for="inPlace">Наличие</label>
                  <input type="checkbox" class="chek custom-control-input" id="inPlace">
                </div>-->

                <div class="buttons">
                  <button type="reset" class="btn reset"></button>
                  <button class="btn save" type="submit" name="save">Применить</button>
                </div>

              </div>

            </div>
          </form>
        </div>
      </div>
    </div>
  </section>

  <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
        </div>
        <div class="modal-body">
          <a href="#newType" role="button"  data-bs-toggle="modal" >новый тип оборудования</a>
          <a href="#newEquipment" role="button"  data-bs-toggle="modal">новое оборудование</a>
          <a href="/equipment-units/-1">новая единица оборудования</a>
        </div>
      </div>
    </div>
  </div>

  <div class="modal fade" id="newType" >
    <div class="modal-dialog modal-dialog-newType modal-dialog-centered">
      <div class="modal-content modal-content-newType">
        <div class="modal-header modal-header-newType">
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
        </div>
        <div class="modal-body modal-body-newType">
          <form method="post" action="/equipment-units/new-equipment-type">
            <div class="forma">
              <input type="text" class="Type_class first" name="Type" placeholder="Тип">
              <div class="modal-buttons">
                <a class="btn-cancel" href="#">oтмeнить</a>
                <button type="submit" class="btn save">
                  Coxpaнить
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

  <div class="modal fade" id="newEquipment" >
    <div class="modal-dialog modal-dialog-newEquipment modal-dialog-centered">
      <div class="modal-content modal-content-newEquipment">
        <div class="modal-header modal-header-newEquipment">
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Закрыть"></button>
        </div>
        <div class="modal-body modal-body-newEquipment">
          <form method="post" action="/equipment-units/new-equipment">
            <div class="forma">
              <select class="Type_class first custom-select" name="Type" placeholder="Тип">
                <#list equipmentTypes as equipmentType>
                  <option class="op1" value="${equipmentType.equipmentTypeId}">${equipmentType.equipmentTypeName}</option>
                </#list>
              </select>
              <input type="text" class="Country_class first" name="Manufacturer" placeholder="Производитель">
              <input type="text" class="Equipment_class first" name="Equipment" placeholder="Оборудование">
              <div class="modal-buttons">
                <a class="btn-cancel" href="#">oтмeнить</a>
                <button class="btn save" type="submit">
                  Coxpaнить
                </button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

  <div class="container table-container">
    <table class="table">
      <thead class="thead">
      <tr>
        <td class="td-1 col-1 row-1" scope="col ">Инв. номер</td>
        <td class="col-2 row-1" scope="col">Тип</td>
        <td class="col-3 row-1" scope="col">Оборудование</td>
        <td class="col-4 row-1" scope="col">Корпус</td>
        <td class="col-5 row-1" scope="col">Аудитория</td>
        <td class="col-6 row-1 rowhere" scope="col">Наличие</td>
        <td class="td-2 col-7 row-1" scope="col"></td>
      </tr>
      </thead>
      <tbody>
      <#list equipmentUnits as equipmentUnit>
        <tr>
          <td class="col-1">${equipmentUnit.inventoryNumber}</td>
          <td class="col-2">${equipmentUnit.equipment.equipmentType.equipmentTypeName}</td>
          <td class="col-3">${equipmentUnit.equipment.modelName}</td>
          <td class="col-4">${equipmentUnit.location.trainingCenter.centerAddress}</td>
          <td class="col-5">${equipmentUnit.location.locationNumber}</td>
          <#if equipmentUnit.onState>
            <td class="col-6"><img src="/static/images/True.svg"></td>
          <#else>
            <td class="col-6"><img src="/static/images/False.svg"></td>
          </#if>
          <td class="col-7"><a class="btn-more" href="/equipment-units/${equipmentUnit.equipmentUnitId}">подробнее</a></td>
        </tr>
      </#list>
      </tbody>
    </table>

    <div class="buttons buttons-bottom">

      <a class="btn page-back" href="/equipment-units/previous">
        <div class="left-arrow"></div>
      </a>

      <button class="btn page">
        ${currentPageNumber}
      </button>

      <a class="btn page-next" href="/equipment-units/next">
        <div class="right-arrow"></div>
      </a>

      <div class="buttons-star-end" >

        <a class="btn btn-start-page" href="/equipment-units/begin">
          Начало
        </a>

        <a class="btn btn-end-page" href="/equipment-units/end">
          Конец
        </a>

      </div>

    </div>

  </div>

</@base.page>